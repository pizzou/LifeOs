package com.lifeos.api.repository;
import com.lifeos.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByUser_IdOrderByIsDefaultDescNameAsc(Long userId);
}