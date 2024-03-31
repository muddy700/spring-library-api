package com.kalambo.libraryapi.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IAuditTrail;
import com.kalambo.libraryapi.responses.IAuditTrailV2;
import com.kalambo.libraryapi.services.AuditTrailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/audit-trails")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Audit-Trail", description = "Manage user activities trails in the system.")

public class AuditTrailController {
    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping
    @Operation(summary = "Retrieve all audit-trails.", description = "Some description.")
    public IPage<IAuditTrailV2> getAllAuditTrails(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET - /api/v1/audit-trails");

        return auditTrailService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    @Operation(summary = "Retrieve a single audit-trail by id.", description = "Some description.")
    public IAuditTrail getAuditTrailById(@PathVariable("id") UUID auditTrailId) {
        log.info("GET - /api/v1/audit-trails/" + auditTrailId);
        
        return auditTrailService.getById(auditTrailId);
    }
}
