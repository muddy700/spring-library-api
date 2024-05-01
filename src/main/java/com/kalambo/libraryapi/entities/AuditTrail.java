package com.kalambo.libraryapi.entities;

import com.kalambo.libraryapi.entities.abstracts.CreationEntity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
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

@Table(name = "audit_trails", indexes = {
        @Index(name = "action_index", columnList = "action"),
        @Index(name = "resource_name_index", columnList = "resource_name"),
        @Index(name = "resource_id_index", columnList = "resource_id"),
        @Index(name = "creation_index", columnList = "created_at"),
        @Index(name = "user_index", columnList = "user_id"),
})

public class AuditTrail extends CreationEntity {
    @Column(length = 20, nullable = false)
    private String action;

    @Column(length = 25, nullable = false)
    private String resourceName;

    private UUID resourceId;

    @Column(columnDefinition = "TEXT")
    private String previousValues;

    @Column(columnDefinition = "TEXT")
    private String updatedValues;

    // Relationships
    @ManyToOne(optional = false)
    private User user;
}