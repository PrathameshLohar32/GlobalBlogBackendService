package com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.Blog;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.Comment;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String userId;

    private String blogId;

    private String comment;

    private List<Comment> replies;

    private Comment parentComment;
}
