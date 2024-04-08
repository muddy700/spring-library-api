package com.kalambo.libraryapi.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.kalambo.libraryapi.responses.ISuccess;
import com.kalambo.libraryapi.responses.IBook;
import com.kalambo.libraryapi.responses.IBookV2;
import com.kalambo.libraryapi.services.AuthService;
import com.kalambo.libraryapi.services.BookService;
import com.kalambo.libraryapi.utilities.GlobalUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book", description = "Manage books.")

public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private GlobalUtil globalUtil;

    @Autowired
    private AuthService authService;

    @PostMapping
    @PreAuthorize("hasAuthority('create_book')")
    @Operation(summary = "Add a book.", description = "Some description.")
    public ISuccess createBook(@Valid @RequestBody BookDto payload) {
        logRequest("POST", "");

        return successResponse("create", bookService.create(payload));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('view_book')")
    @Operation(summary = "Retrieve all books.", description = "Some description.")
    public IPage<IBookV2> getAllBooks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logRequest("GET", "");

        return bookService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('view_book')")
    @Operation(summary = "Retrieve a single book by id.", description = "Some description.")
    public IBook getBookById(@PathVariable("id") UUID bookId) {
        logRequest("GET", "/" + bookId);

        return bookService.getById(bookId);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('update_book')")
    @Operation(summary = "Update book details.", description = "Some description.")
    public ISuccess updateBook(@PathVariable("id") UUID bookId, @RequestBody UpdateBookDto payload) {
        logRequest("PUT", "/" + bookId);

        bookService.update(payload.setId(bookId));
        return successResponse("update", bookId);
    }

    @PostMapping("{id}/reviews")
    @PreAuthorize("hasRole('Student')")
    @Operation(summary = "Add review into a book.", description = "Some description.")
    public ISuccess addReview(@PathVariable("id") UUID bookId, @Valid @RequestBody BookReviewDto payload) {
        logRequest("POST", "/" + bookId + "/reviews");

        bookService.addReview(payload.setBookId(bookId));
        return successResponse("Book's review added", bookId);
    }

    @PutMapping("{bookId}/reviews/{reviewId}")
    @PreAuthorize("hasRole('Student')")
    @Operation(summary = "Update book's review info.", description = "Some description.")
    public ISuccess updateReview(@PathVariable("bookId") UUID bookId,
            @PathVariable("reviewId") UUID reviewId, @Valid @RequestBody UpdateBookReviewDto payload) {
        logRequest("PUT", "/" + bookId + "/reviews/" + reviewId);

        bookService.updateReview(payload.setReviewId(reviewId));
        return successResponse("Book's review updated", reviewId);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('delete_book')")
    @Operation(summary = "Delete a single book by id.", description = "Some description.")
    public ISuccess deleteBookById(@PathVariable("id") UUID bookId) {
        logRequest("DELETE", "/" + bookId);

        bookService.delete(bookId);
        return successResponse("delete", bookId);
    }

    private final ISuccess successResponse(String action, UUID resourceId) {
        return GlobalUtil.formatResponse("Book", action, resourceId);
    }

    private void logRequest(String httpMethod, String endpoint) {
        globalUtil.logRequest(httpMethod, "books" + endpoint, authService.getPrincipalUsername());
    }
}
