package com.kalambo.libraryapi.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Table(name = "tasks", indexes = {
        @Index(name = "title_index", columnList = "title DESC", unique = true),
        @Index(name = "duration_index", columnList = "max_duration")
})

@SQLRestriction("deleted = false")
// @SQLRestriction( "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE tasks SET deleted = true, deleted_at = NOW() WHERE id = ?")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Integer id;

    @Column(length = 100, unique = true, nullable = false)
    private String title;

    @Column(name = "max_duration", nullable = false)
    private Integer maxDuration;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private String authorEmail;

    @Column(name = "published", columnDefinition = "boolean default false")
    private boolean published;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedAt;

    private Boolean deleted = Boolean.FALSE;
    private Date deletedAt;
}
