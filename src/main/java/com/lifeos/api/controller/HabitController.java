package com.lifeos.api.controller;
import com.lifeos.api.dto.*;
import com.lifeos.api.model.*;
import com.lifeos.api.service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;
@RestController @RequestMapping("/api/habits") @RequiredArgsConstructor
public class HabitController {
    private final HabitService s;
    @GetMapping           public ResponseEntity<ApiResponse<List<Habit>>>    getAll()  { return ResponseEntity.ok(ApiResponse.ok(s.getAll())); }
    @GetMapping("/today") public ResponseEntity<ApiResponse<List<HabitLog>>> today()   { return ResponseEntity.ok(ApiResponse.ok(s.getTodayLogs())); }
    @PostMapping          public ResponseEntity<ApiResponse<Habit>> create(@Valid @RequestBody HabitRequest req){ return ResponseEntity.ok(ApiResponse.ok("Created",s.create(req))); }
    @PostMapping("/{id}/log") public ResponseEntity<ApiResponse<HabitLog>> log(@PathVariable Long id,@RequestBody Map<String,Object> b){
        LocalDate date=b.containsKey("date")?LocalDate.parse((String)b.get("date")):null;
        Integer count=b.containsKey("count")?(Integer)b.get("count"):1;
        return ResponseEntity.ok(ApiResponse.ok(s.log(id,date,count,(String)b.get("notes"))));
    }
    @DeleteMapping("/{id}/log") public ResponseEntity<ApiResponse<Void>> deleteLog(@PathVariable Long id,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date){ s.deleteLog(id,date); return ResponseEntity.ok(ApiResponse.ok("Removed",null)); }
    @GetMapping("/{id}/logs")   public ResponseEntity<ApiResponse<List<HabitLog>>> logs(@PathVariable Long id,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate from,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate to){ return ResponseEntity.ok(ApiResponse.ok(s.getLogs(id,from,to))); }
    @DeleteMapping("/{id}")     public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){ s.delete(id); return ResponseEntity.ok(ApiResponse.ok("Deleted",null)); }
}