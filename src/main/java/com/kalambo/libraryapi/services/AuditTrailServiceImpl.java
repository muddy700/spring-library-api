package com.kalambo.libraryapi.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.AuditTrailDto;
import com.kalambo.libraryapi.entities.AuditTrail;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.PageMapper;
import com.kalambo.libraryapi.mappers.AuditTrailMapper;
import com.kalambo.libraryapi.repositories.AuditTrailRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IAuditTrail;
import com.kalambo.libraryapi.responses.IAuditTrailV2;

@Service
public class AuditTrailServiceImpl implements AuditTrailService {
    @Autowired
    private AuditTrailRepository auditTrailRepository;

    @Autowired
    private AuditTrailMapper auditTrailMapper;

    @Autowired
    private PageMapper<AuditTrail, IAuditTrailV2> pageMapper;

    @Override
    public void create(AuditTrailDto payload) {
        auditTrailRepository.save(payload.toEntity().setUser(getPrincipal()));
    }

    @Override
    public IPage<IAuditTrailV2> getAll(Pageable pageable) {
        return pageMapper.paginate(auditTrailRepository.findAll(pageable));
    }

    @Override
    public IAuditTrail getById(UUID auditTrailId) {
        return auditTrailMapper.map(getEntity(auditTrailId));
    }

    @Override
    public AuditTrail getEntity(UUID trailId) {
        String errorMessage = "No audit-trail found with ID: " + trailId;

        return auditTrailRepository.findById(trailId).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    // TODO: Remove this function and reuse from authService
    private User getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken)
            throw new AccessDeniedException("Request not authenticated");

        return (User) auth.getPrincipal();
    }
}
