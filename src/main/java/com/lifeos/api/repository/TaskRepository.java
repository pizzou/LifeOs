package com.lifeos.api.repository;
import com.lifeos.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByUser_IdAndArchivedFalseOrderByPositionAscCreatedAtDesc(Long userId);
    List<Task> findByUser_IdAndStatusAndArchivedFalse(Long userId, String status);
    @Query("SELECT t FROM Task t WHERE t.user.id=:uid AND t.archived=false AND t.dueDate BETWEEN :from AND :to ORDER BY t.dueDate ASC")
    List<Task> findDueBetween(Long uid, LocalDateTime from, LocalDateTime to);
    @Query("SELECT t FROM Task t WHERE t.user.id=:uid AND t.archived=false AND t.dueDate < :now AND t.status <> 'DONE' AND t.status <> 'CANCELLED'")
    List<Task> findOverdue(Long uid, LocalDateTime now);
    long countByUser_IdAndStatusAndArchivedFalse(Long userId, String status);
}