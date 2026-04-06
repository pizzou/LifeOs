package com.lifeos.api.util;
import com.lifeos.api.model.User;
import com.lifeos.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.time.ZoneId;
@Component @RequiredArgsConstructor
public class CurrentUserUtil {
    private final UserRepository userRepo;
    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
    }
    public Long getCurrentUserId(){ return getCurrentUser().getId(); }

    public String getCurrentUserTimezone() {
        String tz = getCurrentUser().getTimezone();
        return (tz == null || tz.isBlank()) ? "UTC" : tz;
    }

    public ZoneId getCurrentUserZoneId() {
        try {
            return ZoneId.of(getCurrentUserTimezone());
        } catch (Exception e) {
            return ZoneId.of("UTC");
        }
    }
}