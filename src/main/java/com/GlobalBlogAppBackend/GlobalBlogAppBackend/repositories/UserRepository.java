package com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByUserIdAndConsumerName(String userId, String consumerName);
}
