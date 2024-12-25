package com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NonNull
    private String userId;

    private String firstName;

    private String lastName;

    @NonNull
    private String email;

    @NonNull
    private String consumerName;

    private String profilePic;
}
