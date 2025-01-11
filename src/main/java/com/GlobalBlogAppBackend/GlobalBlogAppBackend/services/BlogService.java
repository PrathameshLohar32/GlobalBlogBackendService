package com.GlobalBlogAppBackend.GlobalBlogAppBackend.services;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.ApiResponse;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.BlogDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.UpdateBlogRequestDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.Blog;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.User;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ApiException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.BlogRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageUploadService imageUploadService;

    public ResponseEntity<?> createBlog(BlogDTO blogDTO) {
        try {
            Blog blog = new Blog();
            blog.setTitle(blogDTO.getTitle());
            blog.setSubTitle(blogDTO.getSubTitle());
            blog.setContent(blogDTO.getContent());
            blog.setTags(blogDTO.getTags());
            blog.setCategory(blogDTO.getCategory());
            blog.setConsumerName(blogDTO.getConsumer());
            blog.setCreatedAt(new Date());
            blog.setUpdatedAt(new Date());
            List<String> images = new ArrayList<>();
            if (blogDTO.getImages().size() > 2) {
                throw new ApiException("only 2 images are allowed");
            }
            for (MultipartFile image : blogDTO.getImages()) {
                Map response = imageUploadService.upload(image, "blogApp");
                String url = (String) response.get("url");
                images.add(url);
            }
            blog.setImages(images);
            User user = userService.getUser(blogDTO.getUserName(), blogDTO.getConsumer());
            blog.setUser(user);
            return ResponseEntity.ok(blog);
        }catch (Exception e){
            log.error("Error while creating blog {}",e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<?> getBlogById(String blogId){
        try {
            Optional<Blog> blogOptional = blogRepository.findById(blogId);
            if (blogOptional.isEmpty()) {
                throw new ApiException("blog not found");
            }
            return ResponseEntity.ok(blogOptional.get());
        }catch (Exception e){
            log.error("error while getting blog {}",e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<?> getAllBlogsOfaConsumer(
            String consumerName, String sortBy, String category, String searchQuery, int page, int size) {

        try {
            // Create Pageable object with sorting
            Pageable pageable = PageRequest.of(page, size);

            // Create a specification object based on the search and category parameters
            Specification<Blog> spec = (root, query, criteriaBuilder) -> {
                Predicate predicate = criteriaBuilder.equal(root.get("consumerName"), consumerName);

                // Apply search query filter if present
                if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                    Predicate searchPredicate = criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + searchQuery.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("subTitle")), "%" + searchQuery.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + searchQuery.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("tags")), "%" + searchQuery.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), "%" + searchQuery.toLowerCase() + "%")
                    );
                    predicate = criteriaBuilder.and(predicate, searchPredicate);
                }

                // Apply category filter if present
                if (category != null && !category.trim().isEmpty()) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.isMember(category, root.get("category")));
                }

                // Apply sorting (default is by "updatedAt")
                if (sortBy != null) {
                    if ("updatedAt".equals(sortBy)) {
                        query.orderBy(criteriaBuilder.desc(root.get("updatedAt")));
                    } else if ("createdAt".equals(sortBy)) {
                        query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
                    }
                }

                return predicate;
            };

            // Fetch the paginated blogs based on the specification
            Page<Blog> blogPage = blogRepository.findAll(spec, pageable);

            // Return paginated response with necessary metadata
            return ResponseEntity.ok(blogPage);
        } catch (Exception e) {
            log.error("Error while fetching blogs: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<?> deleteBlog(String blogId) {
        try {
            Optional<Blog> blogOptional = blogRepository.findById(blogId);
            if (blogOptional.isEmpty()) {
                throw new ApiException("blog not found");
            }
            blogRepository.deleteById(blogId);
            return ResponseEntity.ok(new ApiResponse("Blog deleted successfully",true));
        }catch (Exception e){
            log.error("Error while deleting blog {}",e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<?> updateBlog(String blogId, UpdateBlogRequestDTO request) {
        try {
            Optional<Blog> blogOptional = blogRepository.findById(blogId);
            if (blogOptional.isEmpty()) {
                throw new ApiException("blog not found");
            }
            Blog blog = blogOptional.get();
            blog.setTitle(request.getTitle());
            blog.setSubTitle(request.getSubTitle());
            blog.setContent(request.getContent());
            blog.setTags(request.getTags());
            blog.setCategory(request.getCategory());
            blog.setUpdatedAt(new Date());
            blogRepository.save(blog);
            return ResponseEntity.ok(blog);
        } catch (Exception e){
            log.error("Error while updating blog {}",e.getMessage());
            throw e;
        }
    }
}
