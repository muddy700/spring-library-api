package com.kalambo.libraryapi.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalambo.libraryapi.dtos.BookDto;
import com.kalambo.libraryapi.dtos.BookReviewDto;
import com.kalambo.libraryapi.dtos.UpdateBookDto;
import com.kalambo.libraryapi.dtos.UpdateBookReviewDto;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IBook;
import com.kalambo.libraryapi.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Book", description = "Manage books.")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    @Operation(summary = "Add a book.", description = "Some description.")
    public ResponseEntity<IBook> createBook(@Valid @RequestBody BookDto payload) {
        log.info("POST - /api/v1/books");
        IBook createdBook = bookService.create(payload);

        return new ResponseEntity<IBook>(createdBook, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Retrieve all books.", description = "Some description.")
    public ResponseEntity<IPage<IBook>> getAllBooks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET - /api/v1/books");

        return ResponseEntity.ok(bookService.getAll(PageRequest.of(page, size)));
    }

    @GetMapping("{id}")
    @Operation(summary = "Retrieve a single book by id.", description = "Some description.")
    public ResponseEntity<IBook> getBookById(@PathVariable("id") UUID bookId) {
        log.info("GET - /api/v1/books/" + bookId);
        IBook book = bookService.getById(bookId);

        return ResponseEntity.ok(book);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update book details.", description = "Some description.")
    public ResponseEntity<IBook> updateBook(@PathVariable("id") UUID bookId,
            @RequestBody UpdateBookDto payload) {
        log.info("PUT - /api/v1/books/" + bookId);

        IBook updatedBook = bookService.update(payload.setId(bookId));

        return ResponseEntity.ok(updatedBook);
    }

    @PostMapping("{id}/reviews")
    @Operation(summary = "Add review into a book.", description = "Some description.")
    public ResponseEntity<IBook> addReview(@PathVariable("id") UUID bookId,
            @Valid @RequestBody BookReviewDto payload) {
        log.info("POST - /api/v1/books/" + bookId + "/reviews");

        IBook updatedBook = bookService.addReview(payload.setBookId(bookId));

        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("{bookId}/reviews/{reviewId}")
    @Operation(summary = "Update book's review info.", description = "Some description.")
    public ResponseEntity<IBook> updateReview(@PathVariable("bookId") UUID bookId,
            @PathVariable("reviewId") UUID reviewId,
            @Valid @RequestBody UpdateBookReviewDto payload) {
        log.info("PUT - /api/v1/books/" + bookId + "/reviews/" + reviewId);

        return ResponseEntity.ok(bookService.updateReview(payload.setReviewId(reviewId)));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a single book by id.", description = "Some description.")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") UUID bookId) {
        log.warn("DELETE - /api/v1/books/" + bookId);

        bookService.delete(bookId);
        String message = "Book with ID: " + bookId + ", deleted successful.";

        return ResponseEntity.ok(message);
    }
}
