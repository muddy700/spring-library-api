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
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.events.BookReviewCreatedEvent;
import com.kalambo.libraryapi.exceptions.ResourceDuplicationException;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.BookReviewMapper;
import com.kalambo.libraryapi.repositories.BookReviewRepository;
import com.kalambo.libraryapi.responses.IBookReview;
import com.kalambo.libraryapi.responses.IBookReviewV2;

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
    public void create(BookReviewDto payload, Book book) {
        User user = userService.getEntity(payload.getUserId());

        checkDuplication(user, book);
        BookReview entity = payload.toEntity().setBook(book).setUser(user);

        publisher.publishEvent(new BookReviewCreatedEvent(bookReviewRepository.save(entity)));
    }

    @Override
    public List<IBookReviewV2> getByBook(Book book) {
        List<IBookReviewV2> response = new ArrayList<IBookReviewV2>();

        bookReviewRepository.findByBook(book)
                .forEach(bookReview -> response.add(bookReviewMapper.mapToV2(bookReview)));

        return response;
    }

    @Override
    public IBookReview getById(UUID bookReviewId) {
        return bookReviewMapper.map(getEntity(bookReviewId));
    }

    @Override
    public void update(UpdateBookReviewDto bookReview) {
        bookReviewRepository.save(copyNonNullValues(bookReview));
    }

    @Override
    public BookReview getEntity(UUID entityId) {
        String errorMessage = "No Book-Review found with ID: " + entityId;

        return bookReviewRepository.findById(entityId).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    private void checkDuplication(User user, Book book) {
        String errorMessage = "Review for a book with title: " + book.getTitle() + ", from user with name: "
                + user.getFullName() + ", already exist";

        if (bookReviewRepository.findByUserAndBook(user, book).isPresent())
            throw new ResourceDuplicationException(errorMessage);
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
