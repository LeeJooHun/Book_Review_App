package com.example.Book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostSummaryResponse {
    private Long id;
    private String title;
    private String nickname;
    private byte[] profileImage;
}
