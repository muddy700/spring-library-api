package com.kalambo.libraryapi.mappers;

import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.responses.IBook;
import com.kalambo.libraryapi.responses.IBookV2;
import com.kalambo.libraryapi.responses.IBookV3;

@Component
public class BookMapper {
    public IBook map(Book book) {
        IBook response = new IBook().setId(book.getId())
                .setTitle(book.getTitle()).setDescription(book.getDescription())
                .setRegistrationNumber(book.getRegistrationNumber()).setEnabled(book.getEnabled())
                .setContent(book.getContent()).setCoverImage(book.getCoverImage()).setAuthorName(book.getAuthorName())
                .setCreatedAt(book.getCreatedAt()).setUpdatedAt(book.getUpdatedAt()).setDeletedAt(book.getDeletedAt());

        return response;
    }

    // TODO: private Integer ratings;
    // TODO: private Integer reviewsCount;

    public IBookV2 mapToV2(Book book) {
        IBookV2 response = new IBookV2().setId(book.getId())
                .setEnabled(book.getEnabled()).setCoverImage(book.getCoverImage())
                .setAuthorName(book.getAuthorName()).setUpdatedAt(book.getUpdatedAt())
                .setTitle(book.getTitle()).setRegistrationNumber(book.getRegistrationNumber());

        return response;
    }

    public IBookV3 mapToV3(Book book) {
        return new IBookV3().setId(book.getId())
                .setTitle(book.getTitle()).setRegistrationNumber(book.getRegistrationNumber());
    }
}