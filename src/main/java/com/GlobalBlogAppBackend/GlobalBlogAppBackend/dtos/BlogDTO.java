package com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.Comment;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {

    private String title;

    private String subTitle;

    private Set<String> category;

    private String content;

    private List<MultipartFile> images;

    private List<String> tags;

    private String userName;

    private String consumer;
}
