package com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsumerCredentialsRepository extends JpaRepository<ConsumerCredentials, String> {
    Optional<ConsumerCredentials> findByEmail(String email);
}
