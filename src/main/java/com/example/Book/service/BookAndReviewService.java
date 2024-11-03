package com.example.Book.service;

import com.example.Book.dto.ReviewDto;
import com.example.Book.entity.Book;
import com.example.Book.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookAndReviewService {

    private final BookService bookService;
    private final ReviewService reviewService;

    public void save(ReviewDto reviewDto) {
        Book book = bookService.save(reviewDto);
        reviewService.save(reviewDto, book);
    }

    public void update(ReviewDto reviewDto) {
        Review target = reviewService.findById(reviewDto.getId());
        bookService.update(target, reviewDto);
        reviewService.update(target, reviewDto);
    }
}
