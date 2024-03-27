package com.kalambo.libraryapi.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.entities.BookReview;
import com.kalambo.libraryapi.responses.IBook;
import com.kalambo.libraryapi.responses.IBookReview;

@Component
public class BookMapper {
    @Autowired
    private BookReviewMapper bookReviewMapper;

    public IBook map(Book book) {
        IBook response = new IBook().setId(book.getId())
                .setReviews(mapBookReviews(book.getReviews()))
                .setTitle(book.getTitle()).setDescription(book.getDescription())
                .setRegistrationNumber(book.getRegistrationNumber()).setEnabled(book.getEnabled())
                .setContent(book.getContent()).setCoverImage(book.getCoverImage()).setAuthorName(book.getAuthorName())
                .setCreatedAt(book.getCreatedAt()).setUpdatedAt(book.getUpdatedAt()).setDeletedAt(book.getDeletedAt());

        return response;
    }

    private List<IBookReview> mapBookReviews(Set<BookReview> bookReviews) {
        List<IBookReview> result = new ArrayList<IBookReview>(bookReviews.size());
        bookReviews.forEach(bookReview -> result.add(bookReviewMapper.map(bookReview)));

        return result;
    }
}