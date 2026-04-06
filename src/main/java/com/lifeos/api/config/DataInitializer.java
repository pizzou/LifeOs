package com.lifeos.api.config;
import com.lifeos.api.model.Account;
import com.lifeos.api.model.User;
import com.lifeos.api.repository.AccountRepository;
import com.lifeos.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration @RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final PasswordEncoder encoder;
    @Bean
    public ApplicationRunner init(){
        return args -> {
            if(!userRepo.existsByEmail("demo@lifeos.app")){
                User u = new User();
                u.setName("Demo User");
                u.setEmail("demo@lifeos.app");
                u.setPassword(encoder.encode("lifeos123"));
                u.setOnboarded(true);
                userRepo.save(u);
                Account a = new Account();
                a.setUser(u); a.setName("Main Account");
                a.setType("BANK"); a.setBalance(5000.0);
                a.setDefault(true); a.setColor("#6366f1");
                accountRepo.save(a);
                System.out.println("[LifeOS] Demo: demo@lifeos.app / lifeos123");
            }
        };
    }
}