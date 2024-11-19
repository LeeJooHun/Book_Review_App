package com.example.Book.service;

import com.example.Book.dto.CommentDto;
import com.example.Book.dto.CommentResponseDto;
import com.example.Book.entity.Comment;
import com.example.Book.entity.Post;
import com.example.Book.entity.User;
import com.example.Book.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public void createComment(Long postId, CommentDto commentDto) {
        User user = userService.findByNickname(commentDto.getNickname());
        Post post = postService.findById(postId);
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .user(user)
                .post(post)
                .build();
        commentRepository.save(comment);
    }

    @Transactional
    public List<CommentResponseDto> getComments(Long postId) {
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        List<Comment> comments = postService.findById(postId).getComments();

        for(Comment comment : comments){
            CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                    .content(comment.getContent())
                    .nickname(comment.getUser().getNickname())
                    .profileImage(comment.getUser().getProfileImage())
                    .build();
            commentResponseDtos.add(commentResponseDto);
        }
        return commentResponseDtos;
    }
}
