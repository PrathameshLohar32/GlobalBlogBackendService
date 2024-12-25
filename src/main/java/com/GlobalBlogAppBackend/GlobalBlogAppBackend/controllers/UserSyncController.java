package com.GlobalBlogAppBackend.GlobalBlogAppBackend.controllers;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.ApiResponse;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.UserDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.services.UserSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserSyncController {

    @Autowired
    private UserSyncService userSyncService;

    @PostMapping("/sync-user")
    public ResponseEntity<ApiResponse> syncUser(@RequestBody UserDTO userDTO){
        return userSyncService.syncUser(userDTO);
    }
    @PostMapping("/sync-users")
    public ResponseEntity<ApiResponse> syncUsers(@RequestBody List<UserDTO> userDTOs){
        return userSyncService.syncUsers(userDTOs);
    }
}
