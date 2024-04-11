package com.kalambo.libraryapi.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalambo.libraryapi.dtos.UserDto;
import com.kalambo.libraryapi.dtos.UpdateUserDto;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.ISuccess;
import com.kalambo.libraryapi.responses.IUser;
import com.kalambo.libraryapi.responses.IUserV2;
import com.kalambo.libraryapi.services.AuthService;
import com.kalambo.libraryapi.services.UserService;
import com.kalambo.libraryapi.utilities.GlobalUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "Manage users.")

public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private GlobalUtil globalUtil;

    @Autowired
    private AuthService authService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Retrieve details of authenticated user by token.", description = "Some description.")
    public IUser getUserProfile() {
        logRequest("GET", "/me");

        return userService.getByToken(SecurityContextHolder.getContext().getAuthentication());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create_user')")
    @Operation(summary = "Create a new user.", description = "Some description.")
    public ISuccess createUser(@Valid @RequestBody UserDto payload) {
        logRequest("POST", "");

        return successResponse("create", userService.create(payload));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('view_user')")
    @Operation(summary = "Retrieve all users.", description = "Some description.")
    public IPage<IUserV2> getAllUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logRequest("GET", "");

        return userService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('view_user')")
    @Operation(summary = "Retrieve a single user by id.", description = "Some description.")
    public IUser getUserById(@PathVariable("id") UUID userId) {
        logRequest("GET", "/" + userId);

        return userService.getById(userId);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('update_user')")
    @Operation(summary = "Update user details.", description = "Some description.")
    public ISuccess updateUser(@PathVariable("id") UUID userId, @Valid @RequestBody UpdateUserDto payload) {
        logRequest("PUT", "/" + userId);

        userService.update(payload.setId(userId));
        return successResponse("update", userId);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('delete_user')")
    @Operation(summary = "Delete a single user by id.", description = "Some description.")
    public ISuccess deleteUserById(@PathVariable("id") UUID userId) {
        logRequest("DELETE", "/" + userId);

        userService.delete(userId);
        return successResponse("delete", userId);
    }

    private final ISuccess successResponse(String action, UUID resourceId) {
        return globalUtil.formatResponse("User", action, resourceId);
    }

    private void logRequest(String httpMethod, String endpoint) {
        globalUtil.logRequest(httpMethod, "users" + endpoint, authService.getPrincipalUsername());
    }
}
