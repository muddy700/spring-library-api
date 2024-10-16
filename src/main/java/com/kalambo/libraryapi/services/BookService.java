package com.kalambo.libraryapi.services;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.kalambo.libraryapi.dtos.BookDto;
import com.kalambo.libraryapi.dtos.BookReviewDto;
import com.kalambo.libraryapi.dtos.UpdateBookDto;
import com.kalambo.libraryapi.dtos.UpdateBookReviewDto;
import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IBook;
import com.kalambo.libraryapi.responses.IBookV2;

public interface BookService {
    UUID create(BookDto bookDto);

    IPage<IBookV2> getAll(Pageable pageable);

    IBook getById(UUID bookId);

    void update(UpdateBookDto payload);

    void delete(UUID bookId);

    Book getEntity(UUID bookId);

    void addReview(BookReviewDto bookReviewDto);

    void updateReview(UpdateBookReviewDto payload);
}
