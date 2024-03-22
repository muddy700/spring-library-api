package com.kalambo.libraryapi.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.AuditTrailDto;
import com.kalambo.libraryapi.entities.AuditTrail;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.PageMapper;
import com.kalambo.libraryapi.mappers.AuditTrailMapper;
import com.kalambo.libraryapi.repositories.AuditTrailRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IAuditTrail;

@Service
public class AuditTrailServiceImpl implements AuditTrailService {
    @Autowired
    private AuditTrailRepository auditTrailRepository;

    @Autowired
    private AuditTrailMapper auditTrailMapper;

    @Autowired
    private PageMapper<AuditTrail, IAuditTrail> pageMapper;

    @Override
    public void create(AuditTrailDto auditTrailDto) {
        auditTrailRepository.save(auditTrailDto.toEntity());
    }

    @Override
    public IPage<IAuditTrail> getAll(Pageable pageable) {
        return pageMapper.paginate(auditTrailRepository.findAll(pageable));
    }

    @Override
    public IAuditTrail getById(UUID auditTrailId) {
        String errorMessage = "No audit-trail found with ID: " + auditTrailId;

        AuditTrail auditTrailInfo = auditTrailRepository.findById(auditTrailId).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage));

        return auditTrailMapper.map(auditTrailInfo);
    }

}
