package com.kalambo.libraryapi.entities.abstracts;

import java.util.Date;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)

public abstract class UpdationEntity extends CreationEntity {
    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedAt;
}
