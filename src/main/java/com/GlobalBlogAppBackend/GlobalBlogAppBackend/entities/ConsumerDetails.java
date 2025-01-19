package com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Consumer_Details")
public class ConsumerDetails {

    @Id
    private String name;

    @OneToOne
    @JoinColumn(name = "credentials_name", referencedColumnName = "name")
    private ConsumerCredentials credentials;

    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "TEXT") // ToDo : changed to json for SQL
    private List<BlogCategory> masterList;

    private String getUserApi;

    private String getUserApiKey;

    @Data
    public static class BlogCategory {
        private String name;
        private String displayName;
    }
}
