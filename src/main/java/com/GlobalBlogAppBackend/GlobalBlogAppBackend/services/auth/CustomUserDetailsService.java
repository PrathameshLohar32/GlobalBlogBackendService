package com.GlobalBlogAppBackend.GlobalBlogAppBackend.services.auth;


import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerCredentials;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerDetails;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.User;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ResourceNotFoundException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.ConsumerCredentialsRepository;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.ConsumerDetailsRepository;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ConsumerCredentialsRepository consumerCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
        Optional<ConsumerCredentials> userOptional;
        if (emailOrUsername.contains("@")) {
            userOptional = consumerCredentialsRepository.findByEmail(emailOrUsername);
        } else {
            userOptional = consumerCredentialsRepository.findById(emailOrUsername);
        }
        ConsumerCredentials consumerCredentials = userOptional.orElseThrow(() ->
                new ResourceNotFoundException("consumerService", "email or username", emailOrUsername));
        return new org.springframework.security.core.userdetails.User(
                consumerCredentials.getName(),
                consumerCredentials.getPassword(),
                new ArrayList<>()
        );
    }

}
