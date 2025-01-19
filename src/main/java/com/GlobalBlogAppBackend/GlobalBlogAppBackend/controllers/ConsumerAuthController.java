package com.GlobalBlogAppBackend.GlobalBlogAppBackend.controllers;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.LoginRequest;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.services.auth.ConsumerAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class ConsumerAuthController {

    @Autowired
    private ConsumerAuthenticationService consumerAuthenticationService;

    @PostMapping("/register-consumer")
    public ResponseEntity<?> registerConsumer(@RequestBody CreateConsumerRequest createConsumerRequest){
        return consumerAuthenticationService.createConsumer(createConsumerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return consumerAuthenticationService.login(loginRequest);
    }
}
