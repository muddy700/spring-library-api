package com.kalambo.libraryapi.entities.abstracts;

import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)

public abstract class CreationEntity extends PrimaryEntity {
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;
}
