package com.lifeos.api.service;
import com.lifeos.api.dto.TransactionRequest;
import com.lifeos.api.model.*;
import com.lifeos.api.repository.*;
import com.lifeos.api.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;
@Service @RequiredArgsConstructor
public class FinanceService {
    private final TransactionRepository txRepo;
    private final AccountRepository accountRepo;
    private final BudgetRepository budgetRepo;
    private final CategoryRepository catRepo;
    private final CurrentUserUtil cu;
    public List<Transaction> getTransactions(){ return txRepo.findByUser_IdOrderByDateDescCreatedAtDesc(cu.getCurrentUserId()); }
    @Transactional
    public Transaction createTx(TransactionRequest req){
        User u=cu.getCurrentUser();
        Account acc=accountRepo.findById(req.getAccountId()).filter(a->a.getUser().getId().equals(u.getId())).orElseThrow(()->new RuntimeException("Account not found"));
        Transaction tx=new Transaction(); tx.setUser(u); tx.setAccount(acc);
        tx.setType(req.getType()); tx.setAmount(req.getAmount()); tx.setCurrency(req.getCurrency());
        tx.setDescription(req.getDescription()); tx.setNotes(req.getNotes()); tx.setDate(req.getDate());
        if(req.getCategoryId()!=null) catRepo.findById(req.getCategoryId()).ifPresent(tx::setCategory);
        if("INCOME".equals(req.getType()))  acc.setBalance(acc.getBalance()+req.getAmount());
        else if("EXPENSE".equals(req.getType())) acc.setBalance(acc.getBalance()-req.getAmount());
        acc.setUpdatedAt(LocalDateTime.now(cu.getCurrentUserZoneId())); accountRepo.save(acc);
        return txRepo.save(tx);
    }
    @Transactional
    public void deleteTx(Long id){
        Transaction tx=txRepo.findById(id).orElseThrow(()->new RuntimeException("Not found"));
        if(!tx.getUser().getId().equals(cu.getCurrentUserId())) throw new RuntimeException("Access denied");
        Account acc=tx.getAccount();
        if("INCOME".equals(tx.getType()))  acc.setBalance(acc.getBalance()-tx.getAmount());
        else if("EXPENSE".equals(tx.getType())) acc.setBalance(acc.getBalance()+tx.getAmount());
        accountRepo.save(acc); txRepo.delete(tx);
    }
    public List<Account> getAccounts(){ return accountRepo.findByUser_IdOrderByIsDefaultDescNameAsc(cu.getCurrentUserId()); }
    @Transactional
    public Account createAccount(Account req){ req.setUser(cu.getCurrentUser()); return accountRepo.save(req); }
    public List<Budget> getBudgets(){ return budgetRepo.findByUser_IdOrderByCreatedAtDesc(cu.getCurrentUserId()); }
    @Transactional
    public Budget createBudget(Budget req){ req.setUser(cu.getCurrentUser()); return budgetRepo.save(req); }
    public Map<String,Object> getReport(int year,int month){
        Long uid=cu.getCurrentUserId();
        LocalDate s=LocalDate.of(year,month,1),e=s.withDayOfMonth(s.lengthOfMonth());
        double inc=Optional.ofNullable(txRepo.sumByTypeAndDateRange(uid,"INCOME",s,e)).orElse(0.0);
        double exp=Optional.ofNullable(txRepo.sumByTypeAndDateRange(uid,"EXPENSE",s,e)).orElse(0.0);
        Map<String,Object> r=new LinkedHashMap<>();
        r.put("year",year); r.put("month",month); r.put("income",inc); r.put("expenses",exp);
        r.put("net",inc-exp); r.put("savingsRate",inc>0?((inc-exp)/inc)*100:0);
        return r;
    }
}