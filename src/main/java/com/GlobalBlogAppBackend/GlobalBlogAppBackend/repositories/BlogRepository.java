package com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.Blog;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String>, JpaSpecificationExecutor<Blog> {
    List<Blog> findByConsumerName(String consumerName);
}
