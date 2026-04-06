package com.lifeos.api.repository;
import com.lifeos.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByUser_IdOrderByNameAsc(Long userId);
}