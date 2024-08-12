package com.sample.crud.repo;

import com.sample.crud.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBy(String by);
    List<Comment> findByDateOfComment(LocalDateTime dateOfComment);
}
