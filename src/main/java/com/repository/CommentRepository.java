package com.repository;

import com.dto.CommentDTO;
import com.entity.CommentEntity;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	Collection<CommentDTO> findByBlogId(Long blogId);}
