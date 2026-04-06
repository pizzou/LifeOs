package com.lifeos.api.service;
import com.lifeos.api.dto.*;
import com.lifeos.api.model.User;
import com.lifeos.api.repository.UserRepository;
import com.lifeos.api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    @Transactional
    public AuthResponse register(RegisterRequest req){
        if(userRepo.existsByEmail(req.getEmail())) throw new RuntimeException("Email already registered");
        User u = new User();
        u.setName(req.getName()); u.setEmail(req.getEmail().toLowerCase().trim());
        u.setPassword(encoder.encode(req.getPassword())); u.setTimezone(req.getTimezone());
        userRepo.save(u);
        return build(u);
    }
    public AuthResponse login(AuthRequest req){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(),req.getPassword()));
        return build(userRepo.findByEmail(req.getEmail()).orElseThrow());
    }
    private AuthResponse build(User u){
        return new AuthResponse(jwtUtil.generateToken(u.getEmail()),
            jwtUtil.generateRefreshToken(u.getEmail()),
            u.getId(),u.getName(),u.getEmail(),u.getAvatarUrl(),u.isPremium(),u.isOnboarded(),u.getTimezone());
    }
}