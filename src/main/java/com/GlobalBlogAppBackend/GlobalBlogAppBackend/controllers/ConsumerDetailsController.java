package com.GlobalBlogAppBackend.GlobalBlogAppBackend.controllers;

import com.GlobalBlogAppBackend.GlobalBlogAppBackend.dtos.ConsumerDetailsDTO;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.entities.ConsumerDetails;
import com.GlobalBlogAppBackend.GlobalBlogAppBackend.services.ConsumerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ConsumerDetailsController {

    @Autowired
    private ConsumerDetailsService consumerDetailsService;

    @PostMapping("/add-category/{consumerName}")
    public ResponseEntity<?> addCategoryToMasterList(@PathVariable String consumerName, @RequestBody ConsumerDetails.BlogCategory category){
        return consumerDetailsService.addCategoryToMasterList(consumerName,category);
    }
    @PostMapping("/add-consumer")
    public ResponseEntity<?> addConsumer(@RequestBody ConsumerDetailsDTO consumerDetailsDTO){
        return consumerDetailsService.addConsumer(consumerDetailsDTO);
    }

    @GetMapping("/get-master-list/{consumerName}")
    public ResponseEntity<?> getMasterList(@PathVariable String consumerName){
        return consumerDetailsService.getMasterList(consumerName);
    }
}
