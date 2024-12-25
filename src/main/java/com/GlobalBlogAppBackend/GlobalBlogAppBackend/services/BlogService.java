package com.GlobalBlogAppBackend.GlobalBlogAppBackend.services;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.BlogDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.Blog;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.User;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ApiException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageUploadService imageUploadService;

    public ResponseEntity<?> createBlog(BlogDTO blogDTO) {
        try {
            Blog blog = new Blog();
            blog.setTitle(blogDTO.getTitle());
            blog.setSubTitle(blogDTO.getSubTitle());
            blog.setContent(blogDTO.getContent());
            blog.setTags(blogDTO.getTags());
            blog.setCategory(blogDTO.getCategory());
            List<String> images = new ArrayList<>();
            if (blogDTO.getImages().size() > 2) {
                throw new ApiException("only 2 images are allowed");
            }
            for (MultipartFile image : blogDTO.getImages()) {
                Map response = imageUploadService.upload(image, "blogApp");
                String url = (String) response.get("url");
                images.add(url);
            }
            blog.setImages(images);
            User user = userService.getUser(blogDTO.getUserName(), blogDTO.getConsumer());
            blog.setUser(user);
            return ResponseEntity.ok(blog);
        }catch (Exception e){
            log.error("Error while creating blog {}",e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<?> getBlogById(String blogId){
        try {
            Optional<Blog> blogOptional = blogRepository.findById(blogId);
            if (blogOptional.isEmpty()) {
                throw new ApiException("blog not found");
            }
            return ResponseEntity.ok(blogOptional.get());
        }catch (Exception e){
            log.error("error while getting blog {}",e.getMessage());
            throw e;
        }
    }
}
