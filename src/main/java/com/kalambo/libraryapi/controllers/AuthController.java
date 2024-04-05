package com.kalambo.libraryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalambo.libraryapi.dtos.LoginDto;
import com.kalambo.libraryapi.responses.ILogin;
import com.kalambo.libraryapi.services.AuthService;
import com.kalambo.libraryapi.utilities.GlobalUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Authentication and authorization endpoints")

public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user.", description = "Some description.")
    public ILogin authenticate(@RequestBody @Valid LoginDto loginPayload) {
        logRequest("POST", "/login");

        return authService.authenticate(loginPayload);
    }

    private void logRequest(String httpMethod, String endpoint) {
        GlobalUtil.logRequest(httpMethod, "auth" + endpoint);
    }
}
