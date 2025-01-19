package com.GlobalBlogAppBackend.GlobalBlogAppBackend.services.auth;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.controllers.CreateConsumerRequest;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.ApiResponse;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.LoginRequest;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.LoginResponse;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerCredentials;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerDetails;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ApiException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ResourceNotFoundException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.ConsumerCredentialsRepository;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.security.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ConsumerAuthenticationService {

    @Autowired
    private ConsumerCredentialsRepository consumerCredentialsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> createConsumer(CreateConsumerRequest createConsumerRequest) {
        try {
            Optional<ConsumerCredentials> consumerCredentialsOptional = consumerCredentialsRepository.findById(createConsumerRequest.getName());
            if(consumerCredentialsOptional.isPresent()){
                throw new ApiException("name already exists");
            }
            Optional<ConsumerCredentials> consumerCredentialsOptional1 = consumerCredentialsRepository.findByEmail(createConsumerRequest.getEmail());
            if(consumerCredentialsOptional1.isPresent()){
                throw new ApiException("email already exists");
            }
            ConsumerCredentials consumerCredentials = new ConsumerCredentials();
            consumerCredentials.setName(createConsumerRequest.getName());
            consumerCredentials.setEmail(createConsumerRequest.getEmail());
            consumerCredentials.setPassword(passwordEncoder.encode(createConsumerRequest.getPassword()));
            ConsumerDetails consumerDetails = new ConsumerDetails();
            consumerDetails.setName(createConsumerRequest.getName());
            consumerCredentials.setConsumerDetails(consumerDetails);
            consumerCredentialsRepository.save(consumerCredentials);

            return ResponseEntity.ok(new ApiResponse("consumer created successfully",true));

        }catch (Exception e){
            log.error("Error while creating consumer {}",e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getNameOrEmail(), loginRequest.getPassword()));
            String nameOrEmail = loginRequest.getNameOrEmail();
            Optional<ConsumerCredentials> consumerCredentials;
            if (nameOrEmail.contains("@")) {
                consumerCredentials = consumerCredentialsRepository.findByEmail(nameOrEmail);
            } else {
                consumerCredentials = consumerCredentialsRepository.findById(nameOrEmail);
            }
            if (consumerCredentials.isEmpty()) {
                throw new ResourceNotFoundException("User", "email or username", nameOrEmail);
            }

            String token = jwtUtils.generateToken(consumerCredentials.get());
            LoginResponse loginResponse = new LoginResponse(consumerCredentials.get().getName(), token);

            return ResponseEntity.ok(loginResponse);

        } catch (AuthenticationException ex) {
            log.error("Authentication failed: {}", ex.getMessage());
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new ApiException("Cannot login: " + e.getMessage());
        }
    }
}
