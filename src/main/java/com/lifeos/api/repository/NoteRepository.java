package com.lifeos.api.repository;
import com.lifeos.api.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface NoteRepository extends JpaRepository<Note,Long> {
    List<Note> findByUser_IdAndIsArchivedFalseOrderByIsPinnedDescUpdatedAtDesc(Long userId);
    @Query("SELECT n FROM Note n WHERE n.user.id=:uid AND n.isArchived=false AND (LOWER(n.title) LIKE %:q% OR LOWER(n.content) LIKE %:q%)")
    List<Note> search(Long uid, String q);
}