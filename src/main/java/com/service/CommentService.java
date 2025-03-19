package com.service;

import java.util.List;

import com.dto.CommentDTO;

public interface CommentService {
    CommentDTO addComment(CommentDTO commentDTO);

	List<CommentDTO> getCommentsByBlogId(Long blogId);

	CommentDTO getCommentById(Long blogId, Long commentId);

	boolean deleteComment(Long blogId, Long commentId);
}
