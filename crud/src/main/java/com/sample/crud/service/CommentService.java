package com.sample.crud.service;

import com.sample.crud.entity.Comment;
import com.sample.crud.repo.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager entityManager;

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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
        Root<Comment> root = query.from(Comment.class);

        List<Predicate> predicates = new ArrayList<>();

        params.forEach((key, value) -> {
            if(value != null) {
                predicates.add(cb.equal(root.get(key), value));
            }
        });

        query.select(root).where(cb.or(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

}
