package com.lifeos.api.repository;
import com.lifeos.api.model.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface HabitLogRepository extends JpaRepository<HabitLog,Long> {
    List<HabitLog> findByHabit_IdAndLogDateBetweenOrderByLogDateAsc(Long habitId, LocalDate from, LocalDate to);
    Optional<HabitLog> findByHabit_IdAndLogDate(Long habitId, LocalDate date);
    List<HabitLog> findByUser_IdAndLogDate(Long userId, LocalDate date);
}