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
        try {
            List<Comment> comments = commentService.getAllComments();
            return new APIResponse<>(HttpStatus.OK.value(), "Comments retrieved successfully", comments, null);
        } catch (Exception e) {
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve comments", null, e.getMessage());
        }
    }

    @GetMapping("/search")
    public APIResponse<List<Comment>> searchComments(@RequestParam(required = false) String username,
                                                     @RequestParam(required = false) LocalDateTime date) {
        try {
            List<Comment> comments;
            if(username != null && date != null) {
                comments = commentService.getCommentsByDateAndUsername(username, date);
            } else if (username != null) {
                comments = commentService.getCommentsByUsername(username);
            } else if (date != null) {
                comments = commentService.getCommentsByDate(date);
            } else {
                throw new IllegalArgumentException("Username or date must be provided");
            }
            return new APIResponse<>(HttpStatus.OK.value(), "Search successful", comments, null);
        } catch (IllegalArgumentException e) {
            return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid search parameters", null, e.getMessage());
        } catch (Exception e) {
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Search failed", null, e.getMessage());
        }
    }

    @GetMapping("/search/any")
    public APIResponse<List<Comment>> searchComments(@RequestParam Map<String, Object> params) {
        try {
            List<Comment> result = commentService.searchComments(params);
            return new APIResponse<>(HttpStatus.OK.value(), "Search successful", result, null);
        } catch (Exception e) {
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Search failed", null, e.getMessage());
        }
    }

    @PostMapping
    public APIResponse<Comment> createComment(@RequestBody Comment comment) {
        try {
            Comment createdComment = commentService.saveComment(comment);
            return new APIResponse<>(HttpStatus.CREATED.value(), "Comment created successfully", createdComment, null);
        } catch (Exception e) {
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create comment", null, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public APIResponse<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        try {
            Comment updatedComment = commentService.updateComment(id, comment);
            return new APIResponse<>(HttpStatus.OK.value(), "Comment updated successfully", updatedComment, null);
        } catch (Exception e) {
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update comment", null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public APIResponse<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return new APIResponse<>(HttpStatus.OK.value(), "Comment deleted successfully", null, null);
        } catch (Exception e) {
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete comment", null, e.getMessage());
        }
    }
}
