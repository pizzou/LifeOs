package com.lifeos.api.controller;
import com.lifeos.api.dto.*;
import com.lifeos.api.model.Goal;
import com.lifeos.api.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/goals") @RequiredArgsConstructor
public class GoalController {
    private final GoalService s;
    @GetMapping            public ResponseEntity<ApiResponse<List<Goal>>> getAll()    { return ResponseEntity.ok(ApiResponse.ok(s.getAll())); }
    @GetMapping("/active") public ResponseEntity<ApiResponse<List<Goal>>> getActive() { return ResponseEntity.ok(ApiResponse.ok(s.getActive())); }
    @PostMapping           public ResponseEntity<ApiResponse<Goal>> create(@Valid @RequestBody GoalRequest req){ return ResponseEntity.ok(ApiResponse.ok("Created",s.create(req))); }
    @PatchMapping("/{id}/progress") public ResponseEntity<ApiResponse<Goal>> progress(@PathVariable Long id,@RequestBody Map<String,Double> b){ return ResponseEntity.ok(ApiResponse.ok(s.updateProgress(id,b.get("value")))); }
    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){ s.delete(id); return ResponseEntity.ok(ApiResponse.ok("Deleted",null)); }
}