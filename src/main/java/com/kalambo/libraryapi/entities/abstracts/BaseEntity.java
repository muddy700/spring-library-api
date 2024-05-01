package com.kalambo.libraryapi.entities.abstracts;

import java.util.Date;
import jakarta.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)

public abstract class BaseEntity extends UpdationEntity {
    private Date deletedAt;
}
