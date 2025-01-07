package com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UpdateBlogRequestDTO {
    private String title;

    private String subTitle;

    private Set<String> category;

    private String content;

    private List<String> tags;

}
