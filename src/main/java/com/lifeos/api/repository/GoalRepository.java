package com.lifeos.api.repository;
import com.lifeos.api.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface GoalRepository extends JpaRepository<Goal,Long> {
    List<Goal> findByUser_IdOrderByCreatedAtDesc(Long userId);
    List<Goal> findByUser_IdAndStatusOrderByDueDateAsc(Long userId, String status);
}