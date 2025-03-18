package com.service;

import java.util.List;

import com.dto.BlogDTO;

public interface BlogService {
    BlogDTO createBlog(BlogDTO blogDTO);
    BlogDTO getBlogById(Long id);
    BlogDTO updateBlog(Long id, BlogDTO blogDTO);
    boolean deleteBlog(Long id);

	List<BlogDTO> findAllBlogs();
}
