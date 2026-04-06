package com.lifeos.api.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class TaskRequest {
    @NotBlank @Size(max=500) private String title;
    private String description;
    private String priority = "MEDIUM";
    private String status   = "TODO";
    private LocalDateTime dueDate;
    private Long categoryId;
    private Long parentId;
    private Integer estimatedMins;
    private String recurrenceRule;
    private boolean pinned = false;
}