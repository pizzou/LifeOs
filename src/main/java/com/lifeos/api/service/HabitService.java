package com.lifeos.api.service;
import com.lifeos.api.dto.HabitRequest;
import com.lifeos.api.model.*;
import com.lifeos.api.repository.*;
import com.lifeos.api.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;
@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository habitRepo;
    private final HabitLogRepository logRepo;
    private final CategoryRepository catRepo;
    private final CurrentUserUtil cu;
    public List<Habit> getAll() {
        return habitRepo.findByUser_IdAndArchivedFalse(cu.getCurrentUserId());
    }
    @Transactional
    public Habit create(HabitRequest req) {
        User u = cu.getCurrentUser();
        Habit h = new Habit();
        h.setUser(u);
        h.setName(req.getName());
        h.setDescription(req.getDescription());
        h.setIcon(req.getIcon());
        h.setColor(req.getColor());
        h.setFrequency(req.getFrequency());
        h.setFrequencyDays(req.getFrequencyDays());
        h.setTargetCount(req.getTargetCount());
        h.setTargetUnit(req.getTargetUnit());
        if (req.getCategoryId() != null) {
            catRepo.findById(req.getCategoryId()).ifPresent(h::setCategory);
        }
        return habitRepo.save(h);
    }
    @Transactional
    public HabitLog log(Long habitId, LocalDate date, Integer count, String notes) {
        User u = cu.getCurrentUser();
        LocalDate logDate = (date != null) ? date : LocalDate.now(cu.getCurrentUserZoneId());
        Habit h = habitRepo.findById(habitId)
            .filter(x -> x.getUser().getId().equals(u.getId()))
            .orElseThrow(() -> new RuntimeException("Habit not found"));
        Optional<HabitLog> existing = logRepo.findByHabit_IdAndLogDate(habitId, logDate);
        HabitLog log = existing.orElse(new HabitLog());
        log.setHabit(h);
        log.setUser(u);
        log.setLogDate(logDate);
        log.setCount(count != null ? count : 1);
        log.setNotes(notes);
        updateStreak(h);
        return logRepo.save(log);
    }
    @Transactional
    public void deleteLog(Long habitId, LocalDate date) {
        logRepo.findByHabit_IdAndLogDate(habitId, date).ifPresent(logRepo::delete);
        habitRepo.findById(habitId).ifPresent(this::updateStreak);
    }
    public List<HabitLog> getLogs(Long habitId, LocalDate from, LocalDate to) {
        return logRepo.findByHabit_IdAndLogDateBetweenOrderByLogDateAsc(habitId, from, to);
    }
    public List<HabitLog> getTodayLogs() {
        return logRepo.findByUser_IdAndLogDate(cu.getCurrentUserId(), LocalDate.now(cu.getCurrentUserZoneId()));
    }
    private void updateStreak(Habit h) {
        LocalDate today = LocalDate.now(cu.getCurrentUserZoneId());
        List<HabitLog> logs = logRepo.findByHabit_IdAndLogDateBetweenOrderByLogDateAsc(
            h.getId(),
            today.minusDays(365),
            today
        );
        Set<LocalDate> dates = new HashSet<>();
        logs.forEach(l -> dates.add(l.getLogDate()));
        int streak = 0;
        LocalDate cur = today;
        while (dates.contains(cur)) {
            streak++;
            cur = cur.minusDays(1);
        }
        h.setCurrentStreak(streak);
        if (streak > h.getLongestStreak()) {
            h.setLongestStreak(streak);
        }
        h.setUpdatedAt(LocalDateTime.now(cu.getCurrentUserZoneId()));
        habitRepo.save(h);
    }
    @Transactional
    public void delete(Long id) {
        Habit h = habitRepo.findById(id)
            .filter(x -> x.getUser().getId().equals(cu.getCurrentUserId()))
            .orElseThrow(() -> new RuntimeException("Habit not found"));
        habitRepo.delete(h);
    }
}