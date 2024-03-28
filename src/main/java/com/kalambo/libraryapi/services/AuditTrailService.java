package com.kalambo.libraryapi.services;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.kalambo.libraryapi.dtos.AuditTrailDto;
import com.kalambo.libraryapi.entities.AuditTrail;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IAuditTrail;

public interface AuditTrailService {
    void create(AuditTrailDto auditTrailDto);

    IPage<IAuditTrail> getAll(Pageable pageable);

    IAuditTrail getById(UUID auditTrailId);

    AuditTrail getEntity(UUID auditTrailId);
}
