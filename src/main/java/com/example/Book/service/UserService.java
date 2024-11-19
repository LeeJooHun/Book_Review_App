package com.example.Book.service;

import com.example.Book.entity.User;
import com.example.Book.repository.ReviewRepository;
import com.example.Book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final BookService bookService;
    private final String uploadDir = "uploads/";

    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean updateUsername(String username, String newNickname) {
        User target = userRepository.findByUsername(username);
        target.setNickname(newNickname);
        userRepository.save(target);
        return true;
    }

    public String findNicknameByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user.getNickname();
    }

    public User saveProfileImage(String username, MultipartFile file) throws IOException {
        User user = userRepository.findByUsername(username);

        user.setProfileImage(file.getBytes());
        return userRepository.save(user);
    }

    public byte[] getProfileImage(String username) {
        User user = userRepository.findByUsername(username);
        return user.getProfileImage();
    }

    public boolean deleteUser(String username) {
        User target = userRepository.findByUsername(username);
        userRepository.delete(target);
        reviewRepository.deleteByUserId(target.getId());
        return true;
    }

    public byte[] getImageByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname);
        return user.getProfileImage();
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }
}
