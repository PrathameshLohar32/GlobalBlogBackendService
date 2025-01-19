package com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NonNull
    private String nameOrEmail;

    @NonNull
    private String password;
}
