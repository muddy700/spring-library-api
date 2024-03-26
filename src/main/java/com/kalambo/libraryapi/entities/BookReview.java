package com.kalambo.libraryapi.entities;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "book_reviews", indexes = {
        @Index(name = "user_index", columnList = "user_id"),
        @Index(name = "book_index", columnList = "book_id"),
        @Index(name = "composite_index", columnList = "user_id, book_id", unique = true),
})

public class BookReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(insertable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer ratings;

    private String comment;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedAt;

    // Relationships
    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Book book;
}
