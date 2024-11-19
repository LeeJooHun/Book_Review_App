package com.example.Book.controller;

import com.example.Book.dto.BookDto;
import com.example.Book.entity.User;
import com.example.Book.repository.UserRepository;
import com.example.Book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
        userService.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());

        Map<String, String> response = new HashMap<>();
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            response.put("message", "Login successful");
            response.put("nickname", user.getNickname()); // 닉네임 추가
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(response);
        } else {
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/user/nickname")
    public ResponseEntity<Map<String, String>> getNickname(@RequestParam String username) {
        String nickname = userService.findNicknameByUsername(username);
        Map<String, String> response = new HashMap<>();
        response.put("nickname", nickname);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(response);
    }

    @PostMapping("/user/username/update")
    public ResponseEntity<String> updateUsername(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String newNickname = request.get("newNickname");

        if (username == null || newNickname == null) {
            return ResponseEntity.badRequest().body("Username and new nickname are required.");
        }

        boolean isUpdated = userService.updateUsername(username, newNickname);
        if (isUpdated) {
            return ResponseEntity.ok("Username updated successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to update username.");
        }
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(
            @RequestParam("username") String username,
            @RequestParam("file") MultipartFile file) {
        try {
            userService.saveProfileImage(username, file);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(@RequestParam String username) {
        byte[] image = userService.getProfileImage(username);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @GetMapping("/image/nickname")
    public ResponseEntity<byte[]> getImageByNickname(@RequestParam String nickname) {
        byte[] image = userService.getImageByNickname(nickname);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @Transactional
    @PostMapping("/user/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String username) {


        boolean isUpdated = userService.deleteUser(username);
        if (isUpdated) {
            return ResponseEntity.ok("Username updated successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to update username.");
        }
    }
}

