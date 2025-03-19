package com.serviceimpl;

import com.dto.CommentDTO;
import com.entity.BlogEntity;
import com.entity.CommentEntity;
import com.exception.ResourceNotFoundException;
import com.repository.BlogRepository;
import com.repository.CommentRepository;
import com.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public  class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    // ✅ Constructor Injection
    public CommentServiceImpl(CommentRepository commentRepository, BlogRepository blogRepository) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
    }

    // ✅ Add a Comment to a Blog
    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        BlogEntity blog = blogRepository.findById(commentDTO.getBlogId())
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + commentDTO.getBlogId()));

        CommentEntity commentEntity = new CommentEntity(blog, commentDTO.getComment());
        CommentEntity savedComment = commentRepository.save(commentEntity);

        return new CommentDTO(savedComment.getId(), savedComment.getBlog().getId(), savedComment.getComment());
    }

    // ✅ Get All Comments for a Blog
    @Override
    public List<CommentDTO> getCommentsByBlogId(Long blogId) {
        if (!blogRepository.existsById(blogId)) {
            throw new ResourceNotFoundException("Blog not found with id: " + blogId);
        }
        return commentRepository.findByBlogId(blogId)
                .stream()
                .map(comment -> new CommentDTO(comment.getId(), comment.getBlog().getId()
, comment.getComment()))
                .collect(Collectors.toList());
    }

    // ✅ Get a Specific Comment by ID
    @Override
    public CommentDTO getCommentById(Long blogId, Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        if (!comment.getBlog().getId().equals(blogId)) {
            throw new ResourceNotFoundException("Comment does not belong to blog with id: " + blogId);
        }

        return new CommentDTO(comment.getId(), comment.getBlog().getId(), comment.getComment());
    }

 // ✅ Delete a Comment
    @Override
    public boolean deleteComment(Long blogId, Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        if (!comment.getBlog().getId().equals(blogId)) {
            throw new ResourceNotFoundException("Comment does not belong to blog with id: " + blogId);
        }

        commentRepository.delete(comment);
        return true;
    }
}
