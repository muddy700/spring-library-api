package com.kalambo.libraryapi.entities.abstracts;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@MappedSuperclass
@Accessors(chain = true)

public abstract class PrimaryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(insertable = false, updatable = false, nullable = false)
    private UUID id;
}
