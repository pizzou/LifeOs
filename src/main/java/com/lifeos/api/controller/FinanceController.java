package com.lifeos.api.controller;
import com.lifeos.api.dto.*;
import com.lifeos.api.model.*;
import com.lifeos.api.service.FinanceService;
import com.lifeos.api.util.CurrentUserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/finance") @RequiredArgsConstructor
public class FinanceController {
    private final FinanceService s;
    private final CurrentUserUtil cu;
    @GetMapping("/transactions")  public ResponseEntity<ApiResponse<List<Transaction>>> getTx()  { return ResponseEntity.ok(ApiResponse.ok(s.getTransactions())); }
    @PostMapping("/transactions") public ResponseEntity<ApiResponse<Transaction>> createTx(@Valid @RequestBody TransactionRequest req){ return ResponseEntity.ok(ApiResponse.ok("Created",s.createTx(req))); }
    @DeleteMapping("/transactions/{id}") public ResponseEntity<ApiResponse<Void>> deleteTx(@PathVariable Long id){ s.deleteTx(id); return ResponseEntity.ok(ApiResponse.ok("Deleted",null)); }
    @GetMapping("/accounts")      public ResponseEntity<ApiResponse<List<Account>>>  getAcc()    { return ResponseEntity.ok(ApiResponse.ok(s.getAccounts())); }
    @PostMapping("/accounts")     public ResponseEntity<ApiResponse<Account>> createAcc(@RequestBody Account req){ return ResponseEntity.ok(ApiResponse.ok("Created",s.createAccount(req))); }
    @GetMapping("/budgets")       public ResponseEntity<ApiResponse<List<Budget>>> getBudgets()  { return ResponseEntity.ok(ApiResponse.ok(s.getBudgets())); }
    @PostMapping("/budgets")      public ResponseEntity<ApiResponse<Budget>> createBudget(@RequestBody Budget req){ return ResponseEntity.ok(ApiResponse.ok("Created",s.createBudget(req))); }
    @GetMapping("/report")        public ResponseEntity<ApiResponse<Map<String,Object>>> report(@RequestParam(defaultValue="0") int year,@RequestParam(defaultValue="0") int month){
        java.time.LocalDate now=java.time.LocalDate.now(cu.getCurrentUserZoneId());
        return ResponseEntity.ok(ApiResponse.ok(s.getReport(year==0?now.getYear():year,month==0?now.getMonthValue():month)));
    }
}