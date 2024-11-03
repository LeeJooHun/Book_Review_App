package com.example.Book.service;

import com.example.Book.dto.ReviewDto;
import com.example.Book.entity.Book;
import com.example.Book.entity.Review;
import com.example.Book.repository.ReviewRepository;
import com.example.Book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public void save(ReviewDto reviewDto, Book book) {
        Review review = reviewDto.toReview();
        review.setUser(userRepository.findByUsername(reviewDto.getUsername()));
        review.setBook(book);
        reviewRepository.save(review);
    }

    public void update(Review target, ReviewDto reviewDto){
        Review review = reviewDto.toReview();
        review.setUser(userRepository.findByUsername(reviewDto.getUsername()));
        review.setBook(target.getBook());
        reviewRepository.save(review);
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }
}
