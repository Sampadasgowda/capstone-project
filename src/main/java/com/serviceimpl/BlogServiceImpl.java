package com.serviceimpl;

import com.dto.BlogDTO;
import com.entity.BlogEntity;
import com.exception.ResourceNotFoundException;
import com.repository.BlogRepository;
import com.service.BlogService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    // ✅ CREATE
    @Override
    public BlogDTO createBlog(BlogDTO blogDTO) {
        BlogEntity blogEntity = convertToEntity(blogDTO);
        BlogEntity savedBlog = blogRepository.save(blogEntity);
        return convertToDTO(savedBlog);
    }

    // ✅ GET BY ID (Better Exception Handling)
    @Override
    public BlogDTO getBlogById(Long id) {
        BlogEntity blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));
        return convertToDTO(blog);
    }

    // ✅ UPDATE (Better Exception Handling)
    @Override
    public BlogDTO updateBlog(Long id, BlogDTO blogDTO) {
        BlogEntity blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        BlogEntity updatedBlog = blogRepository.save(blog);
        
        return convertToDTO(updatedBlog);
    }

    // ✅ DELETE
    @Override  
    public boolean deleteBlog(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new ResourceNotFoundException("Blog not found with id: " + id);
        }
        blogRepository.deleteById(id);
        return true;
    }

    // ✅ GET ALL BLOGS
    @Override
    public List<BlogDTO> findAllBlogs() {
        return blogRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ ENTITY TO DTO CONVERSION
    private BlogDTO convertToDTO(BlogEntity blogEntity) {
        return new BlogDTO(blogEntity.getId(), blogEntity.getTitle(), blogEntity.getContent());
    }

    // ✅ DTO TO ENTITY CONVERSION
    private BlogEntity convertToEntity(BlogDTO blogDTO) {
        return new BlogEntity(blogDTO.getId(),blogDTO.getTitle(), blogDTO.getContent());
    }
}
