package com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog,String> {
}
