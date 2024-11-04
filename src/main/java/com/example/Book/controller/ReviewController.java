package com.example.Book.controller;

import com.example.Book.dto.ReviewDto;
import com.example.Book.dto.ReviewResponseDto;
import com.example.Book.entity.Review;
import com.example.Book.entity.User;
import com.example.Book.service.BookAndReviewService;
import com.example.Book.service.BookService;
import com.example.Book.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    private final BookAndReviewService bookAndReviewService;
    private final BookService bookService;

    @PostMapping("/review/create")
    public ResponseEntity<Map<String, String>> createReview(@RequestBody ReviewDto reviewDto) {
        bookAndReviewService.save(reviewDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Review created successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDto>> readReview(@RequestParam String isbn) {
        List<ReviewResponseDto> reviewResponseDtos = bookService.getReviewsByIsbn(isbn); // isbn을 기반으로 리뷰 조회

        return ResponseEntity.ok(reviewResponseDtos);
    }

    @PostMapping("/review/update")
    public ResponseEntity<Map<String, String>> updateReview(@RequestBody ReviewDto reviewDto) {
        bookAndReviewService.update(reviewDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Review created successfully");
        return ResponseEntity.ok(response);
    }
}
