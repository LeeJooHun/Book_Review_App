package com.example.Book.dto;

import com.example.Book.entity.ContentItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private String title;
    private List<ContentItem> contentList;
    private String nickname;
    private byte[] profileImage;
}
