package com.kalambo.libraryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalambo.libraryapi.dtos.ChangePasswordDto;
import com.kalambo.libraryapi.dtos.ForgotPasswordDto;
import com.kalambo.libraryapi.dtos.LoginDto;
import com.kalambo.libraryapi.responses.ITokenVerification;
import com.kalambo.libraryapi.responses.IForgotPassword;
import com.kalambo.libraryapi.responses.ILogin;
import com.kalambo.libraryapi.responses.ISuccess;
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

    @Autowired
    private GlobalUtil globalUtil;

    @GetMapping("/verify-email")
    @Operation(summary = "Verify account email.", description = "Some description.")
    public ITokenVerification verifyEmail(@RequestParam String token) {
        logRequest("GET", "/verify-email", null);

        return authService.verifyEmail(token);
    }

    @GetMapping("/verify-password-reset-token")
    @Operation(summary = "Verify token for password reset.", description = "Some description.")
    public ITokenVerification verifyPasswordResetToken(@RequestParam String token) {
        logRequest("GET", "/verify-password-reset-token", null);

        return authService.verifyPasswordResetToken(token);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user.", description = "Some description.")
    public ILogin authenticate(@RequestBody @Valid LoginDto loginPayload) {
        logRequest("POST", "/login", loginPayload.getEmail());

        return authService.authenticate(loginPayload);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password.", description = "Some description.")
    public IForgotPassword forgotPassword(@RequestBody @Valid ForgotPasswordDto payload) {
        logRequest("POST", "/forgot-password", null);

        return authService.forgotPassword(payload);
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Change user password.", description = "Some description.")
    public ISuccess changePassword(@RequestBody @Valid ChangePasswordDto payload) {
        logRequest("POST", "/change-password", authService.getPrincipalUsername());

        authService.changePassword(payload);
        return successResponse("Password changed successfully.");
    }

    private final ISuccess successResponse(String message) {
        return new ISuccess(message, authService.getPrincipalId());
    }

    private void logRequest(String httpMethod, String endpoint, String username) {
        if (username == null)
            globalUtil.logRequest(httpMethod, "auth" + endpoint);
        else
            globalUtil.logRequest(httpMethod, "auth" + endpoint, username);
    }
}
