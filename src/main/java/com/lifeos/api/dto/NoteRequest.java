package com.lifeos.api.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class NoteRequest {
    @Size(max=500) private String title;
    private String content;
    private String color = "#ffffff";
    private Long categoryId;
}