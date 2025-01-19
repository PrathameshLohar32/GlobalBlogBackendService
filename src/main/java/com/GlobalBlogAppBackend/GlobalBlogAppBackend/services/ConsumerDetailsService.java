package com.GlobalBlogAppBackend.GlobalBlogAppBackend.services;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.controllers.CreateConsumerRequest;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.ApiResponse;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.ConsumerDetailsDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.LoginRequest;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.LoginResponse;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerCredentials;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerDetails;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ApiException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ResourceNotFoundException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.ConsumerDetailsRepository;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.security.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.security.SecurityConfig.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ConsumerDetailsService {
    @Autowired
    ConsumerDetailsRepository consumerDetailsRepository;


    public ResponseEntity<?> addCategoryToMasterList( ConsumerDetails.BlogCategory category) {
        try {
            String consumerName = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<ConsumerDetails> consumerDetails = consumerDetailsRepository.findById(consumerName);
            if(consumerDetails.isEmpty()){
                throw new ResourceNotFoundException("Consumer","consumerName",consumerName);
            }
            consumerDetails.get().getMasterList().add(category);
            consumerDetailsRepository.save(consumerDetails.get());
            return ResponseEntity.ok(new ApiResponse("category added successfully",true));
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e){
            log.error("something went wrong while adding category {}",e.getMessage());
            throw new ApiException(e.getMessage());
        }
    }

    public ResponseEntity<?> addConsumerDetails(ConsumerDetailsDTO consumerDetailsDTO) {
        try {
            String consumerName = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<ConsumerDetails> consumerDetails = consumerDetailsRepository.findById(consumerName);
            if (consumerDetails.isEmpty()) {
                throw new ResourceNotFoundException("Consumer");
            }
            ConsumerDetails newConsumerDetails = consumerDetails.get();
            newConsumerDetails.setMasterList(consumerDetailsDTO.getMasterList());
            newConsumerDetails.setGetUserApi(consumerDetailsDTO.getGetUserApi());
            newConsumerDetails.setGetUserApiKey(consumerDetailsDTO.getGetUserApikey());
            consumerDetailsRepository.save(newConsumerDetails);
            return ResponseEntity.ok(new ApiResponse("consumer added successfully", true));
        }catch (ApiException e){
            log.error(e.getMessage());
            throw e;
        }catch (Exception e){
            log.error("something went wrong while adding consumer");
            throw e;
        }
    }

    public ResponseEntity<?> getMasterList(String consumerName) {
        try {
            Optional<ConsumerDetails> consumerDetails = consumerDetailsRepository.findById(consumerName);
            if(consumerDetails.isEmpty()){
                throw new ResourceNotFoundException("Consumer","consumerName",consumerName);
            }
            List<ConsumerDetails.BlogCategory> masterlist = consumerDetails.get().getMasterList();
            return ResponseEntity.ok(masterlist);
        } catch (ResourceNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }catch (Exception e){
            log.error("something went wrong while adding category {}",e.getMessage());
            throw new ApiException(e.getMessage());
        }
    }
}

