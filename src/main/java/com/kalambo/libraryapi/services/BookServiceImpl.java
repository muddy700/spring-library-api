package com.kalambo.libraryapi.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.BookDto;
import com.kalambo.libraryapi.dtos.UpdateBookDto;
import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.events.BookCreatedEvent;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.PageMapper;
import com.kalambo.libraryapi.mappers.BookMapper;
import com.kalambo.libraryapi.repositories.BookRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IBook;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private PageMapper<Book, IBook> pageMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public IBook create(BookDto bookDto) {
        Book book = bookRepository.save(bookDto.toEntity().setRegistrationNumber(generateRegNo()));

        publisher.publishEvent(new BookCreatedEvent(book));
        return bookMapper.map(book);
    }

    @Override
    public IPage<IBook> getAll(Pageable pageable) {
        return pageMapper.paginate(bookRepository.findAll(pageable));
    }

    @Override
    public IBook getById(UUID bookId) {
        String errorMessage = "No book found with ID: " + bookId;

        Book bookInfo = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage));

        return bookMapper.map(bookInfo);
    }

    @Override
    public IBook update(UpdateBookDto payload) {
        return bookMapper.map(bookRepository.save(copyNonNullValues(payload)));
    }

    @Override
    public void delete(UUID bookId) {
        // Ensure book is present or throw 404
        getById(bookId);

        // TODO: Delete all relational data here (if any)
        bookRepository.deleteById(bookId);
    }

    private String generateRegNo() {
        int year = LocalDate.now().getYear();
        int totalBooks = (int) bookRepository.count();

        return "BID-" + year + "-" + (1000 + totalBooks + 1);
    }

    private Book copyNonNullValues(UpdateBookDto payload) {
        // Ensure book is present or throw 404
        getById(payload.getId());

        // Get existing book info
        Book bookInfo = bookRepository.findById(payload.getId()).get();

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
}