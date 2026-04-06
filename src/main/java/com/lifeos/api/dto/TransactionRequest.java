package com.lifeos.api.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
@Data
public class TransactionRequest {
    @NotNull private Long accountId;
    private Long categoryId;
    @NotBlank private String type;
    @NotNull @Positive private Double amount;
    private String currency = "USD";
    @Size(max=500) private String description;
    private String notes;
    @NotNull private LocalDate date;
    private boolean isRecurring = false;
    private String recurrenceRule;
}