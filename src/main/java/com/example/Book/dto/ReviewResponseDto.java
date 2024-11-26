package com.example.Book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {
    private Long id;
    private String nickname;
    private String content;
    private double rating;
    private byte[] profileImage;
}
