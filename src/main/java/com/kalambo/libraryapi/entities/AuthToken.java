package com.kalambo.libraryapi.entities;

import java.util.Date;

import com.kalambo.libraryapi.entities.abstracts.CreationEntity;
import com.kalambo.libraryapi.enums.TokenTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Table(name = "auth_tokens", indexes = {
        @Index(name = "type_index", columnList = "type"),
        @Index(name = "user_index", columnList = "user_id"),
        @Index(name = "token_index", columnList = "token", unique = true),
        @Index(name = "user_and_type_index", columnList = "user_id, type", unique = true)
})

public class AuthToken extends CreationEntity {
    @Column(nullable = false, updatable = false, unique = true)
    private String token;

    @Column(nullable = false, updatable = false)
    private Date expiresAt;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private TokenTypeEnum type;

    // Relationships
    @ManyToOne(optional = false)
    private User user;
}
