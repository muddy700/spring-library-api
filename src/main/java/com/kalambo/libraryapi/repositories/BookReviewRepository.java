package com.kalambo.libraryapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.entities.BookReview;
import java.util.List;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, UUID> {
    List<BookReview> findByBook(Book book);
}
