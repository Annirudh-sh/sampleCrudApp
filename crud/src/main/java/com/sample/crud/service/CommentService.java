package com.sample.crud.service;

import com.sample.crud.entity.Comment;
import com.sample.crud.repo.CommentRepository;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Value("${retrieval.records.limit}")
    private int recordsLimit;

    public List<Comment> getAllComments() {
        Pageable pageable = PageRequest.of(0, recordsLimit);
        return commentRepository.findAll(pageable).getContent();
    }

    public List<Comment> getCommentsByUsername(String username) {
        return commentRepository.findByCreatedBy(username);
    }

    public List<Comment> getCommentsByDate(LocalDateTime date) {
        return commentRepository.findByDateOfComment(date);
    }

    public List<Comment> getCommentsByDateAndUsername(String username, LocalDateTime date) {
        return commentRepository.findByCreatedByAndDateOfComment(username, date);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        return commentRepository.findById(id).map(comment -> {
            comment.setCreatedBy(updatedComment.getCreatedBy());
            comment.setText(updatedComment.getText());
            comment.setUpdatedBy(updatedComment.getUpdatedBy());
            comment.setImagePath(updatedComment.getImagePath());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
        comment.setSoftDelete(true);
        commentRepository.save(comment);
    }

    public List<Comment> searchComments(Map<String, Object> params) {
        Specification<Comment> specification = (Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicates = criteriaBuilder.conjunction();

            params.forEach((key, value) -> {
                Path<Object> path = getPath(root, key);
                if (value instanceof String) {
                    predicates.getExpressions().add(criteriaBuilder.like(path.as(String.class), "%" + value + "%"));
                } else{
                    predicates.getExpressions().add(criteriaBuilder.equal(path, value));
                }
            });
            predicates.getExpressions().add(criteriaBuilder.isFalse(root.get("softDelete")));
            return predicates;
        };

        Pageable pageable = PageRequest.of(0, recordsLimit);
        return commentRepository.findAll(specification, pageable).getContent();
    }

    private static <T> Path<T> getPath(Path<?> root, String key) {
        String[] parts = key.split("\\.");
        Path<?> path = root;
        for (String part : parts) {
            path = path.get(part);
        }
        return (Path<T>) path;
    }

}
