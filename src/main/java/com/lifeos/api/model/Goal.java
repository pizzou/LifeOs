package com.lifeos.api.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity @Table(name="goals") @Data @NoArgsConstructor @AllArgsConstructor
public class Goal {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") private User user;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="category_id") private Category category;
    private String title;
    private String description;
    private String type   = "PERSONAL";
    private String status = "ACTIVE";
    private Double targetValue;
    private Double currentValue = 0.0;
    private String unit;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDateTime completedAt;
    private String color = "#6366f1";
    private String icon  = "target";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
