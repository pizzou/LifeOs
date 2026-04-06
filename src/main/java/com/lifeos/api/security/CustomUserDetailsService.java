package com.lifeos.api.security;
import com.lifeos.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepo.findByEmail(email)
            .orElseThrow(()->new UsernameNotFoundException("User not found: "+email));
        return User.builder().username(user.getEmail()).password(user.getPassword()).roles("USER").build();
    }
}