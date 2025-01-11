package com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
    List<Comment> findByBlogId(String blogId);
}
