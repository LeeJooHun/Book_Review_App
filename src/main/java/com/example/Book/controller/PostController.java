package com.example.Book.controller;

import com.example.Book.dto.PostResponseDto;
import com.example.Book.dto.PostSummaryResponse;
import com.example.Book.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostSummaryResponse>> getPosts() {
        List<PostSummaryResponse> postSummaryResponses = postService.getPosts();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(postSummaryResponses);
    }

    @GetMapping("/post/get")
    public ResponseEntity<PostResponseDto> getPost(@RequestParam Long id) {
        PostResponseDto postResponseDto = postService.getPost(id);
        return ResponseEntity.ok().
                contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(postResponseDto);
    }

    @PostMapping("/post/create")
    public ResponseEntity<Map<String, String>> createPost(@RequestBody Map<String, Object> postData) {
        postService.createPost(postData);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Review created successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/post/delete")
    public ResponseEntity<Map<String, String>> deletePost(@RequestParam Long id) {
        postService.deletePost(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Review deleted successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/post/update")
    public ResponseEntity<Map<String, String>> updatePost(@RequestBody Map<String, Object> postData) {
        postService.updatePost(postData);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Review deleted successfully");

        return ResponseEntity.ok(response);
    }
}
