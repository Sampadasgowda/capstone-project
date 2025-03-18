package com.controller;

import com.dto.BlogDTO;
import com.service.BlogService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // Create a blog
    @PostMapping
    public ResponseEntity<BlogDTO> createBlog(@RequestBody BlogDTO blogDTO) {
        BlogDTO createdBlog = blogService.createBlog(blogDTO);
        return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
    }

    // Get blog by ID
    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long id) {
        BlogDTO blogDTO = blogService.getBlogById(id);
        return new ResponseEntity<>(blogDTO, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        List<BlogDTO> blogDTOs = blogService.findAllBlogs();
        return new ResponseEntity<>(blogDTOs, HttpStatus.OK);
    }


    // Update blog by ID
    @PutMapping("/{id}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable Long id, @RequestBody BlogDTO blogDTO) {
        BlogDTO updatedBlog = blogService.updateBlog(id, blogDTO);
        return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
    }

    // Delete blog by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBlog(@PathVariable Long id) {
        boolean isDeleted = blogService.deleteBlog(id);
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
}
