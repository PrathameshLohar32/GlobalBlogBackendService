package com.GlobalBlogAppBackend.GlobalBlogAppBackend.services;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.ApiResponse;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.UserDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.User;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserSyncService {

    @Autowired
    private UserService userService;

    public ResponseEntity<ApiResponse> syncUser(UserDTO userDTO){
        try {
            userService.saveUser(userDTO);
            return ResponseEntity.ok(new ApiResponse("user synced successfully",true));
        }catch (Exception e){
            log.info("Error while Syncing user {}",e.getMessage());
            throw e;
        }
    }
    public ResponseEntity<ApiResponse> syncUsers(List<UserDTO> userDTOs){
        try {
            for(UserDTO userDTO : userDTOs) {
                userService.saveUser(userDTO);
            }
            return ResponseEntity.ok(new ApiResponse("All users synced successfully",true));
        }catch (Exception e){
            log.info("Error while Syncing user {}",e.getMessage());
            throw e;
        }
    }
}
