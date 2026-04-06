package com.lifeos.api.repository;
import com.lifeos.api.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ReminderRepository extends JpaRepository<Reminder,Long> {
    List<Reminder> findByUser_IdAndDismissedFalseOrderByRemindAtAsc(Long userId);
}