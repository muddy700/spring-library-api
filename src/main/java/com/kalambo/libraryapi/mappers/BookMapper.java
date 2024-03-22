package com.kalambo.libraryapi.mappers;

import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.responses.IBook;

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
}