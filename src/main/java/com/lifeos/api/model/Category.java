package com.lifeos.api.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="categories") @Data @NoArgsConstructor @AllArgsConstructor
public class Category {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") private User user;
    private String name;
    private String type;
    private String color = "#6366f1";
    private String icon  = "circle";
    private LocalDateTime createdAt = LocalDateTime.now();
}
