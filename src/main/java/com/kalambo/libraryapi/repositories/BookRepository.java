package com.kalambo.libraryapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalambo.libraryapi.entities.Book;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByTitleAndAuthorName(String title, String authorName);
}
