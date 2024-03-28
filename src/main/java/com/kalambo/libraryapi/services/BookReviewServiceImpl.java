package com.kalambo.libraryapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.BookReviewDto;
import com.kalambo.libraryapi.dtos.UpdateBookReviewDto;
import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.entities.BookReview;
import com.kalambo.libraryapi.events.BookReviewCreatedEvent;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.BookReviewMapper;
import com.kalambo.libraryapi.repositories.BookReviewRepository;
import com.kalambo.libraryapi.responses.IBookReview;

@Service
public class BookReviewServiceImpl implements BookReviewService {
    @Autowired
    private BookReviewRepository bookReviewRepository;

    @Autowired
    private BookReviewMapper bookReviewMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public IBookReview create(BookReviewDto payload, Book book) {
        BookReview entity = payload.toEntity().setBook(book)
                .setUser(userService.getEntity(payload.getUserId()));

        BookReview bookReview = bookReviewRepository.save(entity);
        publisher.publishEvent(new BookReviewCreatedEvent(bookReview));

        return bookReviewMapper.map(bookReview);
    }

    @Override
    public List<IBookReview> getByBook(Book book) {
        List<IBookReview> response = new ArrayList<IBookReview>();

        bookReviewRepository.findByBook(book)
                .forEach(bookReview -> response.add(bookReviewMapper.map(bookReview)));

        return response;
    }

    @Override
    public IBookReview getById(UUID bookReviewId) {
        return bookReviewMapper.map(getEntity(bookReviewId));
    }

    @Override
    public IBookReview update(UpdateBookReviewDto bookReview) {
        return bookReviewMapper.map(bookReviewRepository.save(copyNonNullValues(bookReview)));
    }

    @Override
    public BookReview getEntity(UUID entityId) {
        String errorMessage = "No Book-Review found with ID: " + entityId;

        return bookReviewRepository.findById(entityId).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    private BookReview copyNonNullValues(UpdateBookReviewDto payload) {
        // Get existing book-review info
        BookReview bookReviewInfo = getEntity(payload.getReviewId());

        // Append all updatable fields here.
        if (payload.getComment() != null)
            bookReviewInfo.setComment(payload.getComment());

        return bookReviewInfo;
    }
}
