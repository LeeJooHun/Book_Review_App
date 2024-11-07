package com.example.Book.service;

import com.example.Book.dto.BookDto;
import com.example.Book.dto.ReviewDto;
import com.example.Book.dto.ReviewResponseDto;
import com.example.Book.entity.Book;
import com.example.Book.entity.Review;
import com.example.Book.repository.BookRepository;
import com.example.Book.repository.ReviewRepository;
import com.example.Book.repository.UserRepository;
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
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

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

    public List<ReviewResponseDto> getReviewsByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if(book == null)
            return null;

        List<Review> reviews = book.getReviews();
        List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();

        for (Review review : reviews) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto();
            reviewResponseDto.setId(review.getId());
            reviewResponseDto.setContent(review.getContent());
            reviewResponseDto.setRating(review.getRating());
            reviewResponseDto.setNickname(review.getUser().getNickname());
            reviewResponseDtos.add(reviewResponseDto);

        }
        return reviewResponseDtos;
    }

    public List<BookDto> getMyBookDtos(String username) {
        Long userId = userRepository.findByUsername(username).getId();
        List<Review> reviews = reviewRepository.findByUserId(userId);

        List<BookDto> bookDtos = new ArrayList<>();
        for(Review review : reviews){
            Book book = bookRepository.findById(review.getBook().getId()).orElse(null);
            BookDto bookDto = new BookDto(book.getTitle(), book.getImage(), book.getAverage(), book.getIsbn());
            bookDtos.add(bookDto);
        }

        return bookDtos;
    }

    public void delete(ReviewDto reviewDto) {
        Book book = bookRepository.findByIsbn(reviewDto.getIsbn());
        double sum = book.getAverage() * book.getRatingCount() - reviewDto.getRating();
        book.setRatingCount(book.getRatingCount() - 1);

        if(book.getRatingCount() == 0){
            bookRepository.delete(book);
        }
        else {
            book.setAverage(sum / book.getRatingCount());
        }
    }
}
