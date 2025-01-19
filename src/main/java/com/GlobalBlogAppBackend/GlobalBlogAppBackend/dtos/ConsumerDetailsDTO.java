package com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerDetails;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.JsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumerDetailsDTO {

//    private String name;

    private List<ConsumerDetails.BlogCategory> masterList;

    private String getUserApi;

    private String getUserApikey;
}
