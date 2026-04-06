package com.lifeos.api.repository;
import com.lifeos.api.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BudgetRepository extends JpaRepository<Budget,Long> {
    List<Budget> findByUser_IdOrderByCreatedAtDesc(Long userId);
}