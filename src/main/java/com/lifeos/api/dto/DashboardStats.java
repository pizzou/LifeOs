package com.lifeos.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @AllArgsConstructor @NoArgsConstructor
public class DashboardStats {
    private long totalTasks;
    private long completedTasks;
    private long overdueTasks;
    private long todayTasks;
    private long activeHabits;
    private long completedHabitsToday;
    private long activeGoals;
    private double netWorth;
    private double monthlyIncome;
    private double monthlyExpenses;
    private double savingsRate;
    private long upcomingReminders;
}