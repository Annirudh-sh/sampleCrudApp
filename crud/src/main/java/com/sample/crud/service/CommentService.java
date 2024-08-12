package com.sample.crud.service;

import com.sample.crud.entity.Comment;
import com.sample.crud.repo.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByUsername(String username) {
        return commentRepository.findByBy(username);
    }

    public List<Comment> getCommentsByDate(LocalDateTime date) {
        return commentRepository.findByDateOfComment(date);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        return commentRepository.findById(id).map(comment -> {
            comment.setBy(updatedComment.getBy());
            comment.setText(updatedComment.getText());
            comment.setDateOfComment(updatedComment.getDateOfComment());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}
