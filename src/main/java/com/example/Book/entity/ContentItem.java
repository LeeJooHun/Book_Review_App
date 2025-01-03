package com.example.Book.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class ContentItem {
    private String type; // "text" 또는 "image"

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData; // 이미지일 경우 이미지 데이터를 저장

    @Lob
    private String textData; // 텍스트일 경우 텍스트 데이터를 저장
}
