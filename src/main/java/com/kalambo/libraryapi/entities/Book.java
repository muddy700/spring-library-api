package com.kalambo.libraryapi.entities;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.kalambo.libraryapi.entities.abstracts.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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

public class Book extends BaseEntity {
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
}
