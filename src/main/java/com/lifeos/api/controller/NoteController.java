package com.lifeos.api.controller;
import com.lifeos.api.dto.*;
import com.lifeos.api.model.Note;
import com.lifeos.api.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/notes") @RequiredArgsConstructor
public class NoteController {
    private final NoteService s;
    @GetMapping            public ResponseEntity<ApiResponse<List<Note>>> getAll()                        { return ResponseEntity.ok(ApiResponse.ok(s.getAll())); }
    @GetMapping("/search") public ResponseEntity<ApiResponse<List<Note>>> search(@RequestParam String q)  { return ResponseEntity.ok(ApiResponse.ok(s.search(q))); }
    @PostMapping           public ResponseEntity<ApiResponse<Note>> create(@Valid @RequestBody NoteRequest req){ return ResponseEntity.ok(ApiResponse.ok("Created",s.create(req))); }
    @PutMapping("/{id}")   public ResponseEntity<ApiResponse<Note>> update(@PathVariable Long id,@Valid @RequestBody NoteRequest req){ return ResponseEntity.ok(ApiResponse.ok(s.update(id,req))); }
    @PatchMapping("/{id}/pin") public ResponseEntity<ApiResponse<Note>> pin(@PathVariable Long id){ return ResponseEntity.ok(ApiResponse.ok(s.pin(id))); }
    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){ s.delete(id); return ResponseEntity.ok(ApiResponse.ok("Deleted",null)); }
}