package com.sample.crud.controller;

import com.sample.crud.entity.Comment;
import com.sample.crud.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v2/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/search")
    public List<Comment> searchComments(@RequestParam(required = false) String username,
                                        @RequestParam(required = false) LocalDateTime date) {
        if (username != null) {
            return commentService.getCommentsByUsername(username);
        } else if (date != null) {
            return commentService.getCommentsByDate(date);
        } else {
            throw new IllegalArgumentException("Username or date must be provided");
        }
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.saveComment(comment);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
