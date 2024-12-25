package com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "Consumer_Details")
public class ConsumerDetails {

    @Id
    private String name;

    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "JSON")
    private List<BlogCategory> masterList;

    @Data
    public static class BlogCategory {
        private String name;
        private String displayName;
    }
}