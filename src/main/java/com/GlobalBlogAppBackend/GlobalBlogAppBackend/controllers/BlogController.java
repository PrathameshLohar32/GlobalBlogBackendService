package com.GlobalBlogAppBackend.GlobalBlogAppBackend.controllers;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.BlogDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.UpdateBlogRequestDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerDetails;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.ConsumerDetailsRepository;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {
    @Autowired
    public BlogService blogService;

    @PostMapping("/create-blog")
    public ResponseEntity<?> createBlog(@RequestParam("title") String title,
                                        @RequestParam("subTitle") String subTitle,
                                        @RequestParam("content") String content,
                                        @RequestParam("tags") List<String> tags,
                                        @RequestParam("category") Set<String> category,
                                        @RequestParam("username") String userName,
                                        @RequestParam("consumer_name") String consumer,
                                        @RequestParam("images") List<MultipartFile> images){
        BlogDTO blogDTO = new BlogDTO(title,subTitle,category,content,images,tags,userName,consumer);
        return blogService.createBlog(blogDTO);
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<?> getBlog(@PathVariable(name = "blogId") String blogId){
        return blogService.getBlogById(blogId);
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<?> deleteBlog(@PathVariable(name = "blogId") String blogId){
        return blogService.deleteBlog(blogId);
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<?> updateBlog(@PathVariable(name = "blogId") String blogId,@RequestBody UpdateBlogRequestDTO request){
        return blogService.updateBlog(blogId,request);
    }

    @GetMapping("/{consumerName}")
    public ResponseEntity<?> getAllBlogsOfaConsumer(
            @PathVariable(name = "consumerName") String consumerName,
            @RequestParam(name = "sortBy", defaultValue = "updatedAt") String sortBy,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "search", required = false) String searchQuery,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        return blogService.getAllBlogsOfaConsumer(consumerName, sortBy, category, searchQuery, page, size);
    }
}
