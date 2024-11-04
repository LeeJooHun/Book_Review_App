package com.example.Book.service;

import com.example.Book.entity.User;
import com.example.Book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }
}
