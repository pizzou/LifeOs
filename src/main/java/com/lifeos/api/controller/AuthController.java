package com.lifeos.api.controller;
import com.lifeos.api.dto.*;
import com.lifeos.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register") public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest req){ return ResponseEntity.ok(ApiResponse.ok("Account created",authService.register(req))); }
    @PostMapping("/login")    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest req){ return ResponseEntity.ok(ApiResponse.ok("Login successful",authService.login(req))); }
}