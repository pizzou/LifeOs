package com.lifeos.api.service;
import com.lifeos.api.dto.TaskRequest;
import com.lifeos.api.model.*;
import com.lifeos.api.repository.*;
import com.lifeos.api.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
@Service @RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepo;
    private final CategoryRepository catRepo;
    private final CurrentUserUtil cu;
    public List<Task> getAll(){ return taskRepo.findByUser_IdAndArchivedFalseOrderByPositionAscCreatedAtDesc(cu.getCurrentUserId()); }
    public List<Task> getByStatus(String s){ return taskRepo.findByUser_IdAndStatusAndArchivedFalse(cu.getCurrentUserId(),s); }
    public List<Task> getToday(){
        LocalDate today = LocalDate.now(cu.getCurrentUserZoneId());
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = start.plusDays(1).minusSeconds(1);
        return taskRepo.findDueBetween(cu.getCurrentUserId(), start, end);
    }
    public List<Task> getOverdue(){ return taskRepo.findOverdue(cu.getCurrentUserId(), LocalDateTime.now(cu.getCurrentUserZoneId())); }
    @Transactional
    public Task create(TaskRequest req){
        User u=cu.getCurrentUser(); Task t=new Task();
        t.setUser(u); t.setTitle(req.getTitle()); t.setDescription(req.getDescription());
        t.setPriority(req.getPriority()); t.setStatus(req.getStatus());
        t.setDueDate(req.getDueDate()); t.setEstimatedMins(req.getEstimatedMins());
        t.setRecurrenceRule(req.getRecurrenceRule()); t.setRecurring(req.getRecurrenceRule()!=null);
        t.setPinned(req.isPinned());
        if(req.getCategoryId()!=null) catRepo.findById(req.getCategoryId()).ifPresent(t::setCategory);
        if(req.getParentId()!=null)   taskRepo.findById(req.getParentId()).ifPresent(t::setParent);
        return taskRepo.save(t);
    }
    @Transactional
    public Task update(Long id,TaskRequest req){
        Task t=owned(id); t.setTitle(req.getTitle()); t.setDescription(req.getDescription());
        t.setPriority(req.getPriority()); t.setDueDate(req.getDueDate());
        t.setEstimatedMins(req.getEstimatedMins()); t.setPinned(req.isPinned());
        t.setUpdatedAt(LocalDateTime.now());
        if(req.getCategoryId()!=null) catRepo.findById(req.getCategoryId()).ifPresent(t::setCategory);
        return taskRepo.save(t);
    }
    @Transactional
    public Task complete(Long id){
        Task t=owned(id);
        t.setStatus("DONE");
        t.setCompletedAt(LocalDateTime.now(cu.getCurrentUserZoneId()));
        t.setUpdatedAt(LocalDateTime.now(cu.getCurrentUserZoneId()));
        return taskRepo.save(t);
    }
    @Transactional
    public Task updateStatus(Long id,String status){
        Task t=owned(id);
        t.setStatus(status);
        if("DONE".equals(status)) t.setCompletedAt(LocalDateTime.now(cu.getCurrentUserZoneId()));
        else t.setCompletedAt(null);
        t.setUpdatedAt(LocalDateTime.now(cu.getCurrentUserZoneId()));
        return taskRepo.save(t);
    }
    @Transactional public void delete(Long id){ taskRepo.delete(owned(id)); }
    @Transactional public Task archive(Long id){ Task t=owned(id); t.setArchived(true); return taskRepo.save(t); }
    private Task owned(Long id){ Task t=taskRepo.findById(id).orElseThrow(()->new RuntimeException("Task not found")); if(!t.getUser().getId().equals(cu.getCurrentUserId())) throw new RuntimeException("Access denied"); return t; }
}