package com.kalambo.libraryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IPermission;
import com.kalambo.libraryapi.services.PermissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Permission", description = "Manage user permissions.")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @Operation(summary = "Retrieve all permissions.", description = "Some description.")
    public ResponseEntity<IPage<IPermission>> getAllPermissions(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET - /api/v1/permissions");

        return ResponseEntity.ok(permissionService.getAll(PageRequest.of(page, size)));
    }

}
