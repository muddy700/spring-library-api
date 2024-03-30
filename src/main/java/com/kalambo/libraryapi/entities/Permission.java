package com.kalambo.libraryapi.entities;

import com.kalambo.libraryapi.entities.abstracts.CreationEntity;

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

@Table(name = "permissions", indexes = {
        @Index(name = "action_index", columnList = "action", unique = true),
        @Index(name = "resource_name_index", columnList = "resource_name")
})

public class Permission extends CreationEntity {
    @Column(name = "resource_name", length = 20, nullable = false)
    private String resourceName;

    @Column(length = 30, unique = true, nullable = false)
    private String action;

    @Column(length = 150, unique = true, nullable = false)
    private String description;
}