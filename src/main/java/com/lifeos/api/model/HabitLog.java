package com.lifeos.api.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity @Table(name="habit_logs") @Data @NoArgsConstructor @AllArgsConstructor
public class HabitLog {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="habit_id") private Habit habit;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id")  private User user;
    private LocalDate logDate;
    private Integer count = 1;
    private String notes;
    private LocalDateTime createdAt = LocalDateTime.now();
}
