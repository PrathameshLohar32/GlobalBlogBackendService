package com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerDetailsRepository extends JpaRepository<ConsumerDetails,String> {
}
