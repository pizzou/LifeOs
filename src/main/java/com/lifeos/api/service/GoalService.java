package com.lifeos.api.service;
import com.lifeos.api.dto.GoalRequest;
import com.lifeos.api.model.*;
import com.lifeos.api.repository.*;
import com.lifeos.api.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@Service @RequiredArgsConstructor
public class GoalService {
    private final GoalRepository goalRepo;
    private final CategoryRepository catRepo;
    private final CurrentUserUtil cu;
    public List<Goal> getAll(){ return goalRepo.findByUser_IdOrderByCreatedAtDesc(cu.getCurrentUserId()); }
    public List<Goal> getActive(){ return goalRepo.findByUser_IdAndStatusOrderByDueDateAsc(cu.getCurrentUserId(),"ACTIVE"); }
    @Transactional
    public Goal create(GoalRequest req){
        User u=cu.getCurrentUser(); Goal g=new Goal();
        g.setUser(u); g.setTitle(req.getTitle()); g.setDescription(req.getDescription());
        g.setType(req.getType()); g.setTargetValue(req.getTargetValue()); g.setCurrentValue(req.getCurrentValue());
        g.setUnit(req.getUnit()); g.setStartDate(req.getStartDate()); g.setDueDate(req.getDueDate());
        g.setColor(req.getColor()); g.setIcon(req.getIcon());
        if(req.getCategoryId()!=null) catRepo.findById(req.getCategoryId()).ifPresent(g::setCategory);
        return goalRepo.save(g);
    }
    @Transactional
    public Goal updateProgress(Long id,Double value){
        Goal g=owned(id); g.setCurrentValue(value);
        if(g.getTargetValue()!=null && value>=g.getTargetValue()){ g.setStatus("COMPLETED"); g.setCompletedAt(LocalDateTime.now(cu.getCurrentUserZoneId())); }
        g.setUpdatedAt(LocalDateTime.now(cu.getCurrentUserZoneId())); return goalRepo.save(g);
    }
    @Transactional public void delete(Long id){ goalRepo.delete(owned(id)); }
    private Goal owned(Long id){ Goal g=goalRepo.findById(id).orElseThrow(()->new RuntimeException("Goal not found")); if(!g.getUser().getId().equals(cu.getCurrentUserId())) throw new RuntimeException("Access denied"); return g; }
}