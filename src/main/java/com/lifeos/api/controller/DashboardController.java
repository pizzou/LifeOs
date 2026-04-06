package com.lifeos.api.controller;
import com.lifeos.api.dto.*;
import com.lifeos.api.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/dashboard") @RequiredArgsConstructor
public class DashboardController {
    private final DashboardService s;
    @GetMapping("/stats") public ResponseEntity<ApiResponse<DashboardStats>> stats(){ return ResponseEntity.ok(ApiResponse.ok(s.getStats())); }
}