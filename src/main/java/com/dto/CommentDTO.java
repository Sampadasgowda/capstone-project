package com.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentDTO {

    private Long id;

    @NotNull(message = "Blog ID cannot be null")
    private Long blogId;

    @NotNull(message = "Comment cannot be null")
    @Size(min = 3, max = 200, message = "Comment must be between 3 and 200 characters")
    private String comment;

    public CommentDTO() {
        // Default constructor
    }

    public CommentDTO(Long id, Long blogId, String comment) {
        this.id = id;
        this.blogId = blogId;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
