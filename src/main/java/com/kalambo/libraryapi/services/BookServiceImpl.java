package com.kalambo.libraryapi.services;

import java.time.LocalDate;
import java.util.List;
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
import com.kalambo.libraryapi.utilities.GlobalUtil;
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

    @Autowired
    private GlobalUtil globalUtil;

    @Override
    public UUID create(BookDto bookDto) {
        checkDuplication(bookDto.getTitle(), bookDto.getAuthorName());
        Book book = bookRepository.save(bookDto.toEntity().setRegistrationNumber(generateRegNo()));

        publisher.publishEvent(new BookCreatedEvent(book));
        trackRequest("create", book, bookDto.toString());

        return book.getId();
    }

    @Override
    public IPage<IBookV2> getAll(Pageable pageable) {
        IPage<IBookV2> res = pageMapper.paginate(bookRepository.findAll(pageable));

        return res.setItems(appendReviewsInfo(res.getItems()));
    }

    @Override
    public IBook getById(UUID bookId) {
        return appendReviewsInfo(getEntity(bookId));
    }

    @Override
    public void update(UpdateBookDto payload) {
        Book bookInfoBeforeUpdate = getEntity(payload.getId());

        bookRepository.save(copyNonNullValues(payload));
        trackRequest("update", bookInfoBeforeUpdate, payload.toString());
    }

    @Override
    public void delete(UUID bookId) {
        Book targetBook = getEntity(bookId);

        bookRepository.delete(targetBook);
        trackRequest("delete", targetBook, null);
    }

    @Override
    public Book getEntity(UUID bookId) {
        String errorMessage = "No book found with ID: " + bookId;

        return bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    @Override
    public void addReview(BookReviewDto payload) {
        bookReviewService.create(payload, getEntity(payload.getBookId()));
    }

    @Override
    public void updateReview(UpdateBookReviewDto payload) {
        bookReviewService.update(payload);
    }

    private void checkDuplication(String title, String authorName) {
        String errorMessage = "Book with title: " + title + ", from author: " + authorName + ", already exist";

        if (bookRepository.findByTitleAndAuthorName(title, authorName).isPresent())
            throw new ResourceDuplicationException(errorMessage);
    }

    private final String generateRegNo() {
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

    private IBook appendReviewsInfo(Book book) {
        IBook iBook = bookMapper.map(book).setReviews(bookReviewService.getByBook(book));

        if (!iBook.getReviews().isEmpty())
            iBook.setRatings(calculateRatings(iBook.getReviews()));

        return iBook;
    }

    private List<IBookV2> appendReviewsInfo(List<IBookV2> items) {
        for (IBookV2 iBookV2 : items) {
            List<IBookReviewV2> bookReviews = bookReviewService.getByBook(getEntity(iBookV2.getId()));

            // Append reviews info
            iBookV2.setReviewsCount(bookReviews.size()).setRatings(calculateRatings(bookReviews));
        }

        return items;
    }

    private final Integer calculateRatings(List<IBookReviewV2> bookReviews) {
        Integer ratings = 0;

        for (IBookReviewV2 review : bookReviews) {
            ratings += review.getRatings();
        }

        return (ratings / bookReviews.size());
    }

    private void trackRequest(String action, Book book, String requestDto) {
        globalUtil.trackRequest(action, "Book", book.getId(), book.toString(), requestDto);
    }
}