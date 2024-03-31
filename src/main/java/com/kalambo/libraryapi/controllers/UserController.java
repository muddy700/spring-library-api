package com.kalambo.libraryapi.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.kalambo.libraryapi.services.UserService;
import com.kalambo.libraryapi.utilities.GlobalUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "User", description = "Manage users.")

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user.", description = "Some description.")
    public ISuccess createUser(@Valid @RequestBody UserDto payload) {
        log.info("POST - /api/v1/users");

        return successResponse("create", userService.create(payload));
    }

    @GetMapping
    @Operation(summary = "Retrieve all users.", description = "Some description.")
    public IPage<IUserV2> getAllUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET - /api/v1/users");

        return userService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    @Operation(summary = "Retrieve a single user by id.", description = "Some description.")
    public IUser getUserById(@PathVariable("id") UUID userId) {
        log.info("GET - /api/v1/users/" + userId);

        return userService.getById(userId);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update user details.", description = "Some description.")
    public ISuccess updateUser(@PathVariable("id") UUID userId, @Valid @RequestBody UpdateUserDto payload) {
        log.info("PUT - /api/v1/users/" + userId);

        userService.update(payload.setId(userId));
        return successResponse("update", userId);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a single user by id.", description = "Some description.")
    public ISuccess deleteUserById(@PathVariable("id") UUID userId) {
        log.warn("DELETE - /api/v1/users/" + userId);

        userService.delete(userId);
        return successResponse("delete", userId);
    }

    private final ISuccess successResponse(String action, UUID resourceId) {
        return GlobalUtil.formatResponse("User", action, resourceId);
    }
}
