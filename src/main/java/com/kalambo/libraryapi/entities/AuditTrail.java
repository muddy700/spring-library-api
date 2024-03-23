package com.kalambo.libraryapi.entities;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
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
@Table(name = "audit_trails", indexes = {
        @Index(name = "action_index", columnList = "action"),
        @Index(name = "resource_name_index", columnList = "resource_name"),
        @Index(name = "resource_id_index", columnList = "resource_id"),
        @Index(name = "creation_index", columnList = "created_at"),
        @Index(name = "user_index", columnList = "user_id"),
})

public class AuditTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(insertable = false, updatable = false)
    private UUID id;

    @Column(length = 20, nullable = false)
    private String action;

    @Column(length = 25, nullable = false)
    private String resourceName;

    @Column(length = 50)
    private String resourceId;

    @Column(nullable = false)
    private String description;

    private String previousValues;
    private String updatedValues;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    // Relationships
    @ManyToOne(optional = false)
    private User user;
}