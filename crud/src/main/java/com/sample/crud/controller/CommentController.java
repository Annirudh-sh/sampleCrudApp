package com.sample.crud.controller;

import com.sample.crud.bo.APIResponse;
import com.sample.crud.entity.Comment;
import com.sample.crud.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public APIResponse<List<Comment>> getAllComments() {
        return new APIResponse<>(HttpStatus.OK.value(), "Comments retrieved successfully", commentService.getAllComments(), null);
    }

    @GetMapping("/search")
    public List<Comment> searchComments(@RequestParam(required = false) String username,
                                        @RequestParam(required = false) LocalDateTime date) {
        if(username != null && date != null) {
            return commentService.getCommentsByDateAndUsername(username, date);
        } else if (username != null) {
            return commentService.getCommentsByUsername(username);
        } else if (date != null) {
            return commentService.getCommentsByDate(date);
        } else {
            throw new IllegalArgumentException("Username or date must be provided");
        }
    }

    @GetMapping("/search/any")
    public APIResponse<List<Comment>> searchComments(@RequestParam Map<String, Object> params) {
        List<Comment> result = commentService.searchComments(params);
        return new APIResponse<>(HttpStatus.OK.value(), "Search successful", result, null);
    }

    @PostMapping
    public APIResponse<Comment> createComment(@RequestBody Comment comment) {
        return new APIResponse<>(HttpStatus.CREATED.value(), "Comment created successfully", commentService.saveComment(comment), null);
    }

    @PutMapping("/{id}")
    public APIResponse<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return new APIResponse<>(HttpStatus.OK.value(), "Comment updated successfully", commentService.updateComment(id, comment), null);
    }

    @DeleteMapping("/{id}")
    public APIResponse<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new APIResponse<>(HttpStatus.OK.value(), "Comment deleted successfully", null, null);
    }
}
