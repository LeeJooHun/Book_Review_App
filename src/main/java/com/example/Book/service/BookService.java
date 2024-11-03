package com.example.Book.service;

import com.example.Book.dto.BookDto;
import com.example.Book.dto.ReviewDto;
import com.example.Book.entity.Book;
import com.example.Book.entity.Review;
import com.example.Book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDto> getBookDtos(){
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = books.stream()
                .map(book -> new BookDto(
                        book.getTitle(),
                        book.getImage(),
                        book.getAverage(),
                        book.getIsbn()
                ))
                .collect(Collectors.toList());
        return bookDtos;
    }

    @Transactional
    public Book save(ReviewDto reviewDto){
        Book book = bookRepository.findByIsbn(reviewDto.getIsbn());
        if(book != null){
            double sum = book.getAverage() * book.getRatingCount() + reviewDto.getRating();
            book.setRatingCount(book.getRatingCount() + 1);
            book.setAverage(sum / book.getRatingCount());
            bookRepository.save(book);
        }
        else{
            book = reviewDto.toBook();
            bookRepository.save(book);
        }
        return book;
    }

    @Transactional
    public void update(Review target, ReviewDto reviewDto){
        Book book = bookRepository.findByIsbn(reviewDto.getIsbn());
        double sum = book.getAverage() * book.getRatingCount() - target.getRating() + reviewDto.getRating();
        book.setAverage(sum / book.getRatingCount());
        bookRepository.save(book);
    }
}
