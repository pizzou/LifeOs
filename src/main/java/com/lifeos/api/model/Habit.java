package com.lifeos.api.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity @Table(name="habits") @Data @NoArgsConstructor @AllArgsConstructor
public class Habit {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") private User user;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="category_id") private Category category;
    private String name;
    private String description;
    private String icon      = "check";
    private String color     = "#6366f1";
    private String frequency = "DAILY";
    private String frequencyDays;
    private Integer targetCount = 1;
    private String targetUnit   = "times";
    private boolean isActive    = true;
    private LocalDate startDate = LocalDate.now();
    private boolean archived    = false;
    private Integer currentStreak = 0;
    private Integer longestStreak = 0;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
