package com.example.Book.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReviewResponseDto {
    private String nickname;
    private String content;
    private int rating;
}
