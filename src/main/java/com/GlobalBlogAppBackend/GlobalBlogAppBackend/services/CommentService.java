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
                throw new ResourceNotFoundException("Blog");
            }
            comment.setBlog(blogOptional.get());
            comment.setReplies(List.of());
            Optional<User> userOptional = userRepository.findById(commentDTO.getUserId());
            if (userOptional.isEmpty()) {
                log.info("user not found with id {}", commentDTO.getUserId());
                throw new ResourceNotFoundException("user");
            }
            comment.setUser(userOptional.get());
            return ResponseEntity.ok(comment);
        }catch (Exception e){
            log.error("Error while adding comment {}",e.getMessage());
            throw e;
        }
    }


    public ResponseEntity<?> getCommentById(String commentId) {
        try{
            Optional<Comment>commentOptional = commentRepository.findById(commentId);
            if(commentOptional.isEmpty()){
                throw new ResourceNotFoundException("comment");
            }
            return ResponseEntity.ok(commentOptional.get());
        } catch (Exception e){
            log.error("Error while getting comment");
            throw e;
        }
    }

    public ResponseEntity<?> getCommentsOnBlog(String blogId) {
        try {
            List<Comment>comments = commentRepository.findByBlogId(blogId);
            if(comments.isEmpty()){
                throw new ResourceNotFoundException("comments");
            }
            return ResponseEntity.ok(comments);
        }catch (Exception e){
            log.error("Error while fetching comments for blogId {} {}" ,blogId,e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<?> deleteComment(String commentId) {
        try {
            if(commentRepository.findById(commentId).isEmpty()){
                throw new ResourceNotFoundException("comment");
            }
            commentRepository.deleteById(commentId);
            return ResponseEntity.ok(true);
        }catch (Exception e){
            log.error("error while deleting comment");
            throw e;
        }
    }
}
