package com.GlobalBlogAppBackend.GlobalBlogAppBackend.controllers;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.CommentDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add-comment")
    ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO){
        return commentService.addComment(commentDTO);
    }

    @GetMapping("/get-comment/{commentId}")
    ResponseEntity<?> getComment(@PathVariable(name = "commentId") String commentId){
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/get-comments/{blogId}")
    ResponseEntity<?> getCommentsOnBlog(@PathVariable(name = "blogId") String blogId){
        return commentService.getCommentsOnBlog(blogId);
    }
//    @PutMapping("/update-comment/{commentId}")
//    ResponseEntity<?> updateComment(@PathVariable(name = "commentId") String commentId){
//        return commentService.updateComment(commentId);
//    }
    @DeleteMapping("/delete-comment/{commentId}")
    ResponseEntity<?> deleteComment(@PathVariable(name = "commentId") String commentId){
        return commentService.deleteComment(commentId);
    }
}
