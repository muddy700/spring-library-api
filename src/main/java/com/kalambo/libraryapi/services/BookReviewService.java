package com.kalambo.libraryapi.services;

import java.util.List;
import java.util.UUID;

import com.kalambo.libraryapi.dtos.BookReviewDto;
import com.kalambo.libraryapi.dtos.UpdateBookReviewDto;
import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.entities.BookReview;
import com.kalambo.libraryapi.responses.IBookReview;
import com.kalambo.libraryapi.responses.IBookReviewV2;

public interface BookReviewService {
    IBookReview create(BookReviewDto bookReviewDto, Book book);

    IBookReview getById(UUID bookReviewId);

    IBookReview update(UpdateBookReviewDto bookReviewDto);

    List<IBookReviewV2> getByBook(Book book);

    BookReview getEntity(UUID bookReviewId);
}
