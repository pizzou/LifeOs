package com.lifeos.api.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="reminders") @Data @NoArgsConstructor @AllArgsConstructor
public class Reminder {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") private User user;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="task_id")  private Task task;
    private String title;
    private String description;
    private LocalDateTime remindAt;
    private String timezone;
    private String recurrenceRule;
    private boolean isRecurring = false;
    private String channel = "PUSH";
    private boolean dismissed = false;
    private boolean sent      = false;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
