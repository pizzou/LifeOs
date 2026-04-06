package com.lifeos.api.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class ReminderRequest {
    @NotBlank @Size(max=500) private String title;
    private String description;
    @NotNull private LocalDateTime remindAt;
    private String timezone;
    private String channel = "PUSH";
    private Long taskId;
}