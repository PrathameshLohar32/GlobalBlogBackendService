package com.GlobalBlogAppBackend.GlobalBlogAppBackend.services;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.ApiResponse;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.ConsumerDetailsDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerDetails;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ApiException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.exceptions.ResourceNotFoundException;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.repositories.ConsumerDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ConsumerDetailsService {
    @Autowired
    ConsumerDetailsRepository consumerDetailsRepository;

    public ResponseEntity<?> addCategoryToMasterList(String consumerName, ConsumerDetails.BlogCategory category) {
        try {
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

    public ResponseEntity<?> addConsumer(ConsumerDetailsDTO consumerDetailsDTO) {
        try {
            Optional<ConsumerDetails> consumerDetails = consumerDetailsRepository.findById(consumerDetailsDTO.getName());
            if (consumerDetails.isPresent()) {
                throw new ApiException("Consumer with same name already exists");
            }
            ConsumerDetails newConsumerDetails = new ConsumerDetails();
            newConsumerDetails.setName(consumerDetailsDTO.getName());
            newConsumerDetails.setMasterList(consumerDetailsDTO.getMasterList());
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
