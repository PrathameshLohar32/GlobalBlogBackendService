package com.GlobalBlogAppBackend.GlobalBlogAppBackend.services;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.CommentDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.Blog;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.Comment;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.User;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ApiException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ResourceNotFoundException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.BlogRepository;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.CommentRepository;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> addComment(CommentDTO commentDTO){
        try {
            Comment comment = new Comment();
            comment.setComment(commentDTO.getComment());
            Optional<Blog> blogOptional = blogRepository.findById(commentDTO.getBlogId());
            if (blogOptional.isEmpty()) {
                log.info("blog not found with id {}", commentDTO.getBlogId());
                throw new ResourceNotFoundException("Blog not found");
            }
            comment.setBlog(blogOptional.get());
            comment.setReplies(List.of());
            Optional<User> userOptional = userRepository.findById(commentDTO.getUserId());
            if (userOptional.isEmpty()) {
                log.info("user not found with id {}", commentDTO.getUserId());
                throw new ResourceNotFoundException("user not found");
            }
            comment.setUser(userOptional.get());
            return ResponseEntity.ok(comment);
        }catch (Exception e){
            log.error("Error while adding comment {}",e.getMessage());
            throw e;
        }
    }



}
