package com.example.Book.service;

import com.example.Book.dto.PostResponseDto;
import com.example.Book.dto.PostSummaryResponse;
import com.example.Book.entity.ContentItem;
import com.example.Book.entity.Post;
import com.example.Book.entity.User;
import com.example.Book.repository.PostRepository;
import com.example.Book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostSummaryResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostSummaryResponse> postSummaryResponses = new ArrayList<>();
        for(Post post : posts){
            PostSummaryResponse postSummaryResponse = new PostSummaryResponse(post.getId(), post.getTitle(), post.getUser().getNickname(), post.getUser().getProfileImage());
            postSummaryResponses.add(postSummaryResponse);
        }
        return postSummaryResponses;
    }

    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        PostResponseDto postResponseDto = new PostResponseDto(post.getTitle(), post.getContentList(), post.getUser().getNickname(), post.getUser().getProfileImage());
        return postResponseDto;
    }

    public Long createPost(Map<String, Object> postData) {

        String title = (String) postData.get("title");
        String username = (String) postData.get("username");
        User user = userRepository.findByUsername(username);

        Post post = new Post();
        post.setTitle(title);
        post.setUser(user); // 작성자 설정

        List<Map<String, String>> contentList = (List<Map<String, String>>) postData.get("contentList");

        for (Map<String, String> item : contentList) {
            ContentItem contentItem = new ContentItem();
            contentItem.setType(item.get("type"));

            if ("text".equals(item.get("type"))) {
                contentItem.setTextData(item.get("data"));
            } else if ("image".equals(item.get("type"))) {
                byte[] imageBytes = Base64.getDecoder().decode(item.get("data"));
                contentItem.setImageData(imageBytes);
            }

            post.getContentList().add(contentItem);
        }

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public void deletePost(Long id){
        Post target = postRepository.findById(id).orElse(null);
        postRepository.delete(target);
    }

    public void updatePost(Long postId, Map<String, Object> postData) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        String title = (String) postData.get("title");
        post.setTitle(title);

        post.getContentList().clear();

        List<Map<String, String>> contentList = (List<Map<String, String>>) postData.get("contentList");
        for (Map<String, String> item : contentList) {

            ContentItem contentItem = new ContentItem();
            contentItem.setType(item.get("type"));

            if ("text".equals(item.get("type"))) {
                contentItem.setTextData(item.get("data"));
            } else if ("image".equals(item.get("type"))) {
                byte[] imageBytes = Base64.getDecoder().decode(item.get("data"));
                contentItem.setImageData(imageBytes);
            }

            post.getContentList().add(contentItem);
        }

        postRepository.save(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }
}
