package com.lifeos.api.service;
import com.lifeos.api.dto.NoteRequest;
import com.lifeos.api.model.*;
import com.lifeos.api.repository.*;
import com.lifeos.api.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@Service @RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepo;
    private final CategoryRepository catRepo;
    private final CurrentUserUtil cu;
    public List<Note> getAll(){ return noteRepo.findByUser_IdAndIsArchivedFalseOrderByIsPinnedDescUpdatedAtDesc(cu.getCurrentUserId()); }
    public List<Note> search(String q){ return noteRepo.search(cu.getCurrentUserId(),q.toLowerCase()); }
    @Transactional
    public Note create(NoteRequest req){
        User u=cu.getCurrentUser(); Note n=new Note();
        n.setUser(u); n.setTitle(req.getTitle()); n.setContent(req.getContent()); n.setColor(req.getColor());
        if(req.getCategoryId()!=null) catRepo.findById(req.getCategoryId()).ifPresent(n::setCategory);
        return noteRepo.save(n);
    }
    @Transactional
    public Note update(Long id,NoteRequest req){
        Note n=owned(id); n.setTitle(req.getTitle()); n.setContent(req.getContent()); n.setColor(req.getColor()); n.setUpdatedAt(LocalDateTime.now(cu.getCurrentUserZoneId())); return noteRepo.save(n);
    }
    @Transactional public Note pin(Long id){ Note n=owned(id); n.setPinned(!n.isPinned()); return noteRepo.save(n); }
    @Transactional public void delete(Long id){ noteRepo.delete(owned(id)); }
    private Note owned(Long id){ Note n=noteRepo.findById(id).orElseThrow(()->new RuntimeException("Not found")); if(!n.getUser().getId().equals(cu.getCurrentUserId())) throw new RuntimeException("Access denied"); return n; }
}