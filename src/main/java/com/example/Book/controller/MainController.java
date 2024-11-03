package com.example.Book.controller;

import com.example.Book.dto.BookDto;
import com.example.Book.dto.ReviewDto;
import com.example.Book.entity.Book;
import com.example.Book.service.BookService;
import com.example.Book.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getBooks() {
        List<BookDto> bookDtos = bookService.getBookDtos();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8) // UTF-8 설정
                .body(bookDtos);
    }

}
