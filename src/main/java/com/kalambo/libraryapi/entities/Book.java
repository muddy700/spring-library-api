package com.kalambo.libraryapi.entities;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "books", indexes = {
        @Index(name = "registration_number_index", columnList = "registration_number", unique = true),
        @Index(name = "title_index", columnList = "title"),
        @Index(name = "description_index", columnList = "description", unique = true),
        @Index(name = "author_index", columnList = "author_name"),
        @Index(name = "enabled_index", columnList = "enabled"),
        @Index(name = "deleted_index", columnList = "deleted_at"),
})

@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE books SET deleted_at = NOW() WHERE id = ?")

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(insertable = false, updatable = false)
    private UUID id;

    @Column(length = 50, unique = true, nullable = false, updatable = false)
    private String registrationNumber;

    @Column(length = 150, nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String coverImage;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private Boolean enabled = Boolean.TRUE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<BookReview> reviews;

    private Date deletedAt;
}
