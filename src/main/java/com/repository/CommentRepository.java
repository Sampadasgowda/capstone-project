package com.repository;

import com.dto.CommentDTO;
import com.entity.CommentEntity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	List<CommentEntity> findByBlogId(Long blogId);

	}
