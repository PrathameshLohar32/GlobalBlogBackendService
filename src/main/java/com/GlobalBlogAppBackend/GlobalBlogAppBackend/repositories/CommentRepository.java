package com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
}
