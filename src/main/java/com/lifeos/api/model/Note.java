package com.lifeos.api.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="notes") @Data @NoArgsConstructor @AllArgsConstructor
public class Note {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") private User user;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="category_id") private Category category;
    private String title;
    @Column(columnDefinition="TEXT") private String content;
    private boolean isPinned   = false;
    private boolean isArchived = false;
    private String color = "#ffffff";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
