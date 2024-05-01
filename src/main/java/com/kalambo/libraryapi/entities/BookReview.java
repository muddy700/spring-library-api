package com.kalambo.libraryapi.entities;

import com.kalambo.libraryapi.entities.abstracts.UpdationEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity

@Table(name = "book_reviews", indexes = {
        @Index(name = "user_index", columnList = "user_id"),
        @Index(name = "book_index", columnList = "book_id"),
        @Index(name = "composite_index", columnList = "user_id, book_id", unique = true),
})

public class BookReview extends UpdationEntity {
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer ratings;

    private String comment;

    // Relationships
    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Book book;
}
