package com.kalambo.libraryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalambo.libraryapi.responses.IAdminSummary;
import com.kalambo.libraryapi.services.AuthService;
import com.kalambo.libraryapi.services.DashboardService;
import com.kalambo.libraryapi.utilities.GlobalUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "Endpoints for retrieving dashboards summaries.")

public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private GlobalUtil globalUtil;

    @Autowired
    private AuthService authService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('Admin')")
    @Operation(summary = "Retrieve dashboard-summaries for the system admin.", description = "Some description.")
    public IAdminSummary getAdminSummary() {
        logRequest("GET", "/admin");

        return dashboardService.getAdminSummary();
    }

    private void logRequest(String httpMethod, String endpoint) {
        globalUtil.logRequest(httpMethod, "dashboard" + endpoint, authService.getPrincipalUsername());
    }
}
