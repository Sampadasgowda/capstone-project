package com.controller;

import com.dto.BlogDTO;
import com.exception.ResourceNotFoundException;
import com.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // ✅ Create a Blog
    @PostMapping
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody BlogDTO blogDTO) {
        BlogDTO createdBlog = blogService.createBlog(blogDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBlog);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogById(id)); // No Optional
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable Long id, @Valid @RequestBody BlogDTO blogDTO) {
        return ResponseEntity.ok(blogService.updateBlog(id, blogDTO)); // No Optional
    }


    // ✅ Delete a Blog by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        if (!blogService.deleteBlog(id)) {
            throw new ResourceNotFoundException("Blog not found with id: " + id);
        }
        return ResponseEntity.noContent().build(); // ✅ Returns HTTP 204 No Content
    }

    // ✅ Get all Blogs
    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        List<BlogDTO> blogs = blogService.findAllBlogs();
        return ResponseEntity.ok(blogs);
    }
}
