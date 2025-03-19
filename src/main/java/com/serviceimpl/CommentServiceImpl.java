package com.serviceimpl;

import com.dto.CommentDTO;
import com.entity.CommentEntity;
import com.exception.ResourceNotFoundException;
import com.repository.BlogRepository;
import com.repository.CommentRepository;
import com.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BlogRepository blogRepository) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
    }

    // ✅ Add a Comment to a Blog
    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        if (!blogRepository.existsById(commentDTO.getBlogId())) {
            throw new ResourceNotFoundException("Blog not found with id: " + commentDTO.getBlogId());
        }
        CommentEntity commentEntity = new CommentEntity(commentDTO.getBlogId(), commentDTO.getComment());
        CommentEntity savedComment = commentRepository.save(commentEntity);
        return new CommentDTO(savedComment.getId(), savedComment.getBlogId(), savedComment.getComment());
    }

    // ✅ Get All Comments for a Blog
    @Override
    public List<CommentDTO> getCommentsByBlogId(Long blogId) {
        if (!blogRepository.existsById(blogId)) {
            throw new ResourceNotFoundException("Blog not found with id: " + blogId);
        }
        return commentRepository.findByBlogId(blogId)
                .stream()
                .map(comment -> new CommentDTO(comment.getId(), comment.getBlogId(), comment.getComment()))
                .collect(Collectors.toList());
    }

    // ✅ Get a Specific Comment by ID
    @Override
    public CommentDTO getCommentById(Long blogId, Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        if (!comment.getBlogId().equals(blogId)) {
            throw new ResourceNotFoundException("Comment does not belong to blog with id: " + blogId);
        }

        return new CommentDTO(comment.getId(), comment.getBlogId(), comment.getComment());
    }

    // ✅ Delete a Comment
    @Override
    public boolean deleteComment(Long blogId, Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        if (!comment.getBlogId().equals(blogId)) {
            throw new ResourceNotFoundException("Comment does not belong to blog with id: " + blogId);
        }

        commentRepository.delete(comment);
        return true;
    }
}
