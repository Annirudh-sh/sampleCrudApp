package com.sample.crud.repo;

import com.sample.crud.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    @Query(value = "SELECT * FROM comments WHERE created_by = ?1 AND soft_delete = false", nativeQuery = true)
    List<Comment> findByCreatedBy(String by);

    @Query(value = "SELECT * FROM comments WHERE dateofcomment = ?1 AND soft_delete = false", nativeQuery = true)
    List<Comment> findByDateOfComment(LocalDateTime dateOfComment);

    @Query(value = "SELECT * FROM comments WHERE created_by = ?1 AND dateofcomment = ?2 AND soft_delete = false", nativeQuery = true)
    List<Comment> findByCreatedByAndDateOfComment(String username, LocalDateTime date);
}
