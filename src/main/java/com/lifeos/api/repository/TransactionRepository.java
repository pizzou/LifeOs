package com.lifeos.api.repository;
import com.lifeos.api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByUser_IdOrderByDateDescCreatedAtDesc(Long userId);
    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.user.id=:uid AND t.type=:type AND t.date BETWEEN :from AND :to")
    Double sumByTypeAndDateRange(Long uid, String type, LocalDate from, LocalDate to);
}