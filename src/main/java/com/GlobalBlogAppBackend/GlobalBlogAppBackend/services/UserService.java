package com.GlobalBlogAppBackend.GlobalBlogAppBackend.services;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.ApiResponse;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.UserDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.User;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ResourceNotFoundException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<ApiResponse> saveUser(UserDTO userDTO) {
        try{
            User user = userRepository.findByUserIdAndConsumerName(userDTO.getUserId(),userDTO.getConsumerName());
            if(user == null){
                user = new User();
            }
            user.setUserId(userDTO.getUserId());
            user.setEmail(user.getEmail());
            user.setName(userDTO.getName());
            user.setConsumerName(user.getConsumerName());

            userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse("user saved successfully",true));
        }catch (Exception e){
            log.info("Error while saving user {}",e.getMessage());
            throw e;
        }
    }

    public User getUser(String username,String consumer){
        try {
            User user = userRepository.findByUserIdAndConsumerName(username, consumer);
            if (user != null) {
                return user;
            } else {
                throw new ResourceNotFoundException("user");
            }
        }catch (Exception e){
            log.error("error while getting user {}", e.getMessage());
            throw e;
        }
    }
}
