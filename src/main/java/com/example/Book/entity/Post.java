package com.example.Book.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 게시글 제목

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // user_id 컬럼으로 관계 설정
    private User user; // 작성자 정보

    @ElementCollection
    @CollectionTable(name = "content_list", joinColumns = @JoinColumn(name = "post_id"))
    private List<ContentItem> contentList = new ArrayList<>();
}

