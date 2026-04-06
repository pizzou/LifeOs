package com.lifeos.api.repository;
import com.lifeos.api.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface HabitRepository extends JpaRepository<Habit,Long> {
    List<Habit> findByUser_IdAndArchivedFalse(Long userId);
    List<Habit> findByUser_IdAndArchivedFalseAndIsActiveTrue(Long userId);
}