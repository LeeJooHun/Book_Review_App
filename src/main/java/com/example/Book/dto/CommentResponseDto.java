package com.example.Book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private String content;
    private String nickname;
    private byte[] profileImage;

    @Builder
    public CommentResponseDto(String content, String nickname, byte[] profileImage) {
        this.content = content;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
