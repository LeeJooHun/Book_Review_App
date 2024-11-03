package com.example.Book.controller;

import com.example.Book.dto.ReviewDto;
import com.example.Book.entity.User;
import com.example.Book.service.BookAndReviewService;
import com.example.Book.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    private final BookAndReviewService bookAndReviewService;

    @PostMapping("/review/create")
    public ResponseEntity<Map<String, String>> createReview(@RequestBody ReviewDto reviewDto) {
        bookAndReviewService.save(reviewDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Review created successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/review/update")
    public ResponseEntity<Map<String, String>> updateReview(@RequestBody ReviewDto reviewDto) {
        bookAndReviewService.update(reviewDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Review created successfully");
        return ResponseEntity.ok(response);
    }
}
