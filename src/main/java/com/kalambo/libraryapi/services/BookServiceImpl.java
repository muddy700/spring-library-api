package com.kalambo.libraryapi.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.BookDto;
import com.kalambo.libraryapi.dtos.BookReviewDto;
import com.kalambo.libraryapi.dtos.UpdateBookDto;
import com.kalambo.libraryapi.dtos.UpdateBookReviewDto;
import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.events.BookCreatedEvent;
import com.kalambo.libraryapi.exceptions.ResourceDuplicationException;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.PageMapper;
import com.kalambo.libraryapi.mappers.BookMapper;
import com.kalambo.libraryapi.repositories.BookRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IBook;
import com.kalambo.libraryapi.responses.IBookReviewV2;
import com.kalambo.libraryapi.responses.IBookV2;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private PageMapper<Book, IBookV2> pageMapper;

    @Autowired
    private BookReviewService bookReviewService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public IBook create(BookDto bookDto) {
        checkDuplication(bookDto.getTitle(), bookDto.getAuthorName());
        Book book = bookRepository.save(bookDto.toEntity().setRegistrationNumber(generateRegNo()));

        publisher.publishEvent(new BookCreatedEvent(book));
        return appendRatingsInfo(book);
    }

    @Override
    public IPage<IBookV2> getAll(Pageable pageable) {
        return pageMapper.paginate(bookRepository.findAll(pageable));
    }

    @Override
    public IBook getById(UUID bookId) {
        return appendRatingsInfo(getEntity(bookId));
    }

    @Override
    public IBook update(UpdateBookDto payload) {
        return appendRatingsInfo(bookRepository.save(copyNonNullValues(payload)));
    }

    @Override
    public void delete(UUID bookId) {
        bookRepository.delete(getEntity(bookId));
    }

    @Override
    public Book getEntity(UUID bookId) {
        String errorMessage = "No book found with ID: " + bookId;

        return bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    @Override
    public IBook addReview(BookReviewDto payload) {
        Book book = getEntity(payload.getBookId());
        bookReviewService.create(payload, book);

        return appendRatingsInfo(book);
    }

    @Override
    public IBook updateReview(UpdateBookReviewDto payload) {
        return appendRatingsInfo(getEntity(bookReviewService.update(payload).getBookId()));
    }

    private void checkDuplication(String title, String authorName) {
        String errorMessage = "Book with title: " + title + ", from author: " + authorName + ", already exist";

        if (bookRepository.findByTitleAndAuthorName(title, authorName).isPresent())
            throw new ResourceDuplicationException(errorMessage);
    }

    private String generateRegNo() {
        int year = LocalDate.now().getYear();
        int totalBooks = (int) bookRepository.count();

        return "BID-" + year + "-" + (1000 + totalBooks + 1);
    }

    private Book copyNonNullValues(UpdateBookDto payload) {
        // Get existing book info
        Book bookInfo = getEntity(payload.getId());

        if (payload.getTitle() != null && payload.getAuthorName() != null)
            checkDuplication(payload.getTitle(), payload.getAuthorName());

        // Append all updatable fields here.
        if (payload.getTitle() != null)
            bookInfo.setTitle(payload.getTitle());

        if (payload.getDescription() != null)
            bookInfo.setDescription(payload.getDescription());

        if (payload.getContent() != null)
            bookInfo.setContent(payload.getContent());

        if (payload.getCoverImage() != null)
            bookInfo.setCoverImage(payload.getCoverImage());

        if (payload.getAuthorName() != null)
            bookInfo.setAuthorName(payload.getAuthorName());

        if (payload.getEnabled() != null)
            bookInfo.setEnabled(payload.getEnabled());

        return bookInfo;
    }

    private IBook appendRatingsInfo(Book book) {
        return appendRatingsInfo(bookMapper.map(book).setReviews(bookReviewService.getByBook(book)));
    }

    private IBook appendRatingsInfo(IBook iBook) {
        Integer ratings = 0;

        if (iBook.getReviews().isEmpty())
            return iBook;

        for (IBookReviewV2 review : iBook.getReviews()) {
            ratings += review.getRatings();
        }

        return iBook.setRatings(ratings / iBook.getReviews().size());
    }
}