package com.lifeos.api.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
@Data
public class GoalRequest {
    @NotBlank @Size(max=500) private String title;
    private String description;
    private String type  = "PERSONAL";
    private Double targetValue;
    private Double currentValue = 0.0;
    private String unit;
    @NotNull private LocalDate startDate;
    private LocalDate dueDate;
    private String color = "#6366f1";
    private String icon  = "target";
    private Long categoryId;
}