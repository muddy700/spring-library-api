package com.kalambo.libraryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IPermissionV2;
import com.kalambo.libraryapi.services.PermissionService;
import com.kalambo.libraryapi.utilities.GlobalUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/permissions")
@Tag(name = "Permission", description = "Manage user permissions.")

public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('view_permission')")
    @Operation(summary = "Retrieve all permissions.", description = "Some description.")
    public IPage<IPermissionV2> getAllPermissions(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logRequest("GET", "");

        return permissionService.getAll(PageRequest.of(page, size));
    }

    private void logRequest(String httpMethod, String endpoint) {
        GlobalUtil.logRequest(httpMethod, "permissions" + endpoint);
    }
}
