package com.example.Book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    private String title;
    private String image;
    private double average;
    private String isbn;
}