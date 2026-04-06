package com.lifeos.api.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class HabitRequest {
    @NotBlank @Size(max=255) private String name;
    private String description;
    private String icon      = "check";
    private String color     = "#6366f1";
    private String frequency = "DAILY";
    private String frequencyDays;
    private Integer targetCount = 1;
    private String targetUnit   = "times";
    private Long categoryId;
}