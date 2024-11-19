package com.example.Book.controller;

import com.example.Book.dto.CommentDto;
import com.example.Book.dto.CommentResponseDto;
import com.example.Book.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{postId}/comments/get")
    public ResponseEntity<Map<String, Object>> getComments(@PathVariable Long postId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<CommentResponseDto> commentResponseDtos = commentService.getComments(postId);
            response.put("message", "댓글이 성공적으로 저장되었습니다.");
            response.put("comments", commentResponseDtos);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(response); // 200 OK 응답
        } catch (Exception e) {
            response.put("error", "댓글 저장에 실패했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 Internal Server Error 응답
        }
    }

    @PostMapping("/{postId}/comment/create")
    public ResponseEntity<Map<String, String>> createComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        Map<String, String> response = new HashMap<>();
        try {
            commentService.createComment(postId, commentDto);
            response.put("message", "댓글을 성공적으로 불러왔습니다.");
            return ResponseEntity.ok(response); // 200 OK 응답
        } catch (Exception e) {
            response.put("error", "댓글을 불러오는데 실패했습니다.: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 Internal Server Error 응답
        }
    }

}
