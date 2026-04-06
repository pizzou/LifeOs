package com.lifeos.api.controller;
import com.lifeos.api.dto.*;
import com.lifeos.api.model.Task;
import com.lifeos.api.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController @RequestMapping("/api/tasks") @RequiredArgsConstructor
public class TaskController {
    private final TaskService s;
    @GetMapping              public ResponseEntity<ApiResponse<List<Task>>> getAll()              { return ResponseEntity.ok(ApiResponse.ok(s.getAll())); }
    @GetMapping("/today")    public ResponseEntity<ApiResponse<List<Task>>> getToday()            { return ResponseEntity.ok(ApiResponse.ok(s.getToday())); }
    @GetMapping("/overdue")  public ResponseEntity<ApiResponse<List<Task>>> getOverdue()          { return ResponseEntity.ok(ApiResponse.ok(s.getOverdue())); }
    @GetMapping("/status/{st}") public ResponseEntity<ApiResponse<List<Task>>> getByStatus(@PathVariable String st){ return ResponseEntity.ok(ApiResponse.ok(s.getByStatus(st))); }
    @PostMapping             public ResponseEntity<ApiResponse<Task>> create(@Valid @RequestBody TaskRequest req){ return ResponseEntity.ok(ApiResponse.ok("Created",s.create(req))); }
    @PutMapping("/{id}")     public ResponseEntity<ApiResponse<Task>> update(@PathVariable Long id,@Valid @RequestBody TaskRequest req){ return ResponseEntity.ok(ApiResponse.ok(s.update(id,req))); }
    @PatchMapping("/{id}/complete") public ResponseEntity<ApiResponse<Task>> complete(@PathVariable Long id){ return ResponseEntity.ok(ApiResponse.ok(s.complete(id))); }
    @PatchMapping("/{id}/status")   public ResponseEntity<ApiResponse<Task>> status(@PathVariable Long id,@RequestBody Map<String,String> b){ return ResponseEntity.ok(ApiResponse.ok(s.updateStatus(id,b.get("status")))); }
    @PatchMapping("/{id}/archive")  public ResponseEntity<ApiResponse<Task>> archive(@PathVariable Long id){ return ResponseEntity.ok(ApiResponse.ok(s.archive(id))); }
    @DeleteMapping("/{id}")  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){ s.delete(id); return ResponseEntity.ok(ApiResponse.ok("Deleted",null)); }
}