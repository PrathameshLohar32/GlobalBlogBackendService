package com.GlobalBlogAppBackend.GlobalBlogAppBackend.controllers;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.CommentDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add-comment")
    ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO){
        return commentService.addComment(commentDTO);
    }
}
