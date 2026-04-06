package com.lifeos.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data @AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String name;
    private String email;
    private String avatarUrl;
    private boolean premium;
    private boolean onboarded;
    private String timezone;
}