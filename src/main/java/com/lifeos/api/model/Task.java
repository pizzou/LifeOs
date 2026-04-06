package com.lifeos.api.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="tasks") @Data @NoArgsConstructor @AllArgsConstructor
public class Task {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") private User user;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="category_id") private Category category;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="parent_id") private Task parent;
    private String title;
    private String description;
    private String priority = "MEDIUM";
    private String status   = "TODO";
    private LocalDateTime dueDate;
    private LocalDateTime completedAt;
    private Integer estimatedMins;
    private String recurrenceRule;
    private boolean isRecurring = false;
    private boolean pinned   = false;
    private boolean archived = false;
    private Integer position = 0;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
