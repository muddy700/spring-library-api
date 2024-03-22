package com.kalambo.libraryapi.services;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.kalambo.libraryapi.dtos.BookDto;
import com.kalambo.libraryapi.dtos.UpdateBookDto;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IBook;

public interface BookService {
    IBook create(BookDto bookDto);

    IPage<IBook> getAll(Pageable pageable);

    IBook getById(UUID bookId);

    IBook update(UpdateBookDto payload);

    void delete(UUID bookId);
}
