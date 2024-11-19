package com.example.Book.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // user_id 컬럼으로 관계 설정
    private User user; // 작성자 정보

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false) // post_id 컬럼으로 관계 설정
    private Post post; // 게시글 정보

    @Builder
    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }
}
