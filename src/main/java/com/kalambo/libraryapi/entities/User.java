package com.kalambo.libraryapi.entities;

import java.util.Date;
import java.util.UUID;

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
@Table(name = "users", indexes = {
        @Index(name = "email_index", columnList = "email", unique = true),
        @Index(name = "deleted_index", columnList = "deleted_at"),
        @Index(name = "phone_index", columnList = "phoneNumber", unique = true),
        @Index(name = "name_index", columnList = "fullName", unique = true),
        @Index(name = "role_index", columnList = "role_id"),
})

@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(insertable = false, updatable = false)
    private UUID id;

    @Column(length = 40, unique = true, nullable = false)
    private String email;

    @Column(length = 12, unique = true, nullable = false)
    private String phoneNumber;

    private Integer phoneVerificationToken;
    private Date verificationTokenExpiresAt;

    @Column(nullable = false)
    private String password = "123";

    private Date emailVerifiedAt;
    private Date phoneVerifiedAt;

    @Column(length = 50, unique = true, nullable = false)
    private String fullName;

    @Column(length = 1, nullable = false)
    private String gender;

    @Column(nullable = false)
    private Boolean firstTimeLogin = Boolean.TRUE;

    @Column(nullable = false)
    private Boolean enabled = Boolean.TRUE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedAt;

    private Date deletedAt;

    // Relationships
    @ManyToOne(optional = false)
    private Role role;

}
