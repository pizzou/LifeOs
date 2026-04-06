package com.lifeos.api.service;
import com.lifeos.api.dto.DashboardStats;
import com.lifeos.api.repository.*;
import com.lifeos.api.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.Optional;
@Service @RequiredArgsConstructor
public class DashboardService {
    private final TaskRepository taskRepo;
    private final HabitRepository habitRepo;
    private final HabitLogRepository logRepo;
    private final GoalRepository goalRepo;
    private final TransactionRepository txRepo;
    private final AccountRepository accountRepo;
    private final ReminderRepository reminderRepo;
    private final CurrentUserUtil cu;
    public DashboardStats getStats(){
        Long uid=cu.getCurrentUserId();
        LocalDate today=LocalDate.now(cu.getCurrentUserZoneId());
        LocalDate ms=today.withDayOfMonth(1),me=today.withDayOfMonth(today.lengthOfMonth());
        LocalDateTime now=LocalDateTime.now(cu.getCurrentUserZoneId());
        LocalDateTime ts=today.atStartOfDay(),te=ts.plusDays(1).minusSeconds(1);
        long total=taskRepo.countByUser_IdAndStatusAndArchivedFalse(uid,"TODO")+taskRepo.countByUser_IdAndStatusAndArchivedFalse(uid,"IN_PROGRESS");
        long done=taskRepo.countByUser_IdAndStatusAndArchivedFalse(uid,"DONE");
        long overdue=taskRepo.findOverdue(uid,now).size();
        long todayTasks=taskRepo.findDueBetween(uid,ts,te).size();
        long activeHabits=habitRepo.findByUser_IdAndArchivedFalseAndIsActiveTrue(uid).size();
        long completedToday=logRepo.findByUser_IdAndLogDate(uid,today).size();
        long activeGoals=goalRepo.findByUser_IdAndStatusOrderByDueDateAsc(uid,"ACTIVE").size();
        double netWorth=accountRepo.findByUser_IdOrderByIsDefaultDescNameAsc(uid).stream().mapToDouble(a->a.getBalance()!=null?a.getBalance():0).sum();
        double inc=Optional.ofNullable(txRepo.sumByTypeAndDateRange(uid,"INCOME",ms,me)).orElse(0.0);
        double exp=Optional.ofNullable(txRepo.sumByTypeAndDateRange(uid,"EXPENSE",ms,me)).orElse(0.0);
        double savings=inc>0?((inc-exp)/inc)*100:0;
        long upcoming=reminderRepo.findByUser_IdAndDismissedFalseOrderByRemindAtAsc(uid).stream().filter(r->r.getRemindAt().isAfter(now)).count();
        return new DashboardStats(total,done,overdue,todayTasks,activeHabits,completedToday,activeGoals,netWorth,inc,exp,savings,upcoming);
    }
}