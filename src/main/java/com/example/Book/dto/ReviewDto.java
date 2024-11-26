package com.example.Book.dto;


import com.example.Book.entity.Book;
import com.example.Book.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReviewDto {

    private Long id;
    private String username;
    private String isbn;
    private String content;
    private double rating;
    private String title;
    private String image;

    public Review toReview(){
        return new Review(id, content, rating, LocalDate.now(), null, null);
    }

    public Book toBook(){
        return new Book(null, isbn, title, image, rating, 1, null);
    }
}
