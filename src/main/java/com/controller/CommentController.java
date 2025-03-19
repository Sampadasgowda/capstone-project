package com.controller;

import com.dto.CommentDTO;
import com.exception.ResourceNotFoundException;
import com.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/blogs/{blogId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // ✅ Post a Comment on a Blog
    @PostMapping
    public ResponseEntity<CommentDTO> postComment(@PathVariable Long blogId, @Valid @RequestBody CommentDTO commentDTO) {
        commentDTO.setBlogId(blogId);
        return new ResponseEntity<>(commentService.addComment(commentDTO), HttpStatus.CREATED);
    }

    // ✅ Get All Comments for a Blog 
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable Long blogId) {
        return ResponseEntity.ok(commentService.getCommentsByBlogId(blogId));
    }

    // ✅ Get a Specific Comment by ID
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long blogId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(blogId, commentId));
    }

    
}
