package com.serviceimpl;

import com.dto.BlogDTO;
import com.entity.BlogEntity;
import com.exception.ResourceNotFoundException;
import com.repository.BlogRepository;
import com.service.BlogService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    // Create a blog
    @Override
    public BlogDTO createBlog(BlogDTO blogDTO) {
        BlogEntity blogEntity = convertToEntity(blogDTO);
        BlogEntity savedBlog = blogRepository.save(blogEntity);
        return convertToDTO(savedBlog);
    }

    // Get blog by id
    @Override
    public BlogDTO getBlogById(Long id) {
        BlogEntity blogEntity = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        return convertToDTO(blogEntity);
    }
    
 // Get all blogs
    @Override
    public List<BlogDTO> findAllBlogs() {
        List<BlogEntity> blogs = blogRepository.findAll();
        List<BlogDTO> blogDTOs = new ArrayList<>();
        
        for (BlogEntity blog : blogs) {
            blogDTOs.add(convertToDTO(blog));
        }
        
        return blogDTOs;
    }


    // Update a blog
    @Override
    public BlogDTO updateBlog(Long id, BlogDTO blogDTO) {
        BlogEntity blogEntity = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        blogEntity.setTitle(blogDTO.getTitle());
        blogEntity.setContent(blogDTO.getContent());

        BlogEntity updatedBlog = blogRepository.save(blogEntity);
        return convertToDTO(updatedBlog);
    }


    // Delete a blog
    @Override
    public boolean deleteBlog(Long id) {
        BlogEntity blogEntity = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        blogRepository.delete(blogEntity);
        return true;
    }

    // Convert BlogDTO to BlogEntity
    private BlogEntity convertToEntity(BlogDTO blogDTO) {
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setTitle(blogDTO.getTitle());
        blogEntity.setContent(blogDTO.getContent());
        return blogEntity;
    }

 // Convert BlogEntity to BlogDTO
    private BlogDTO convertToDTO(BlogEntity blogEntity) {
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setId(blogEntity.getId());  
        blogDTO.setTitle(blogEntity.getTitle());
        blogDTO.setContent(blogEntity.getContent());
        return blogDTO;
    }

}
