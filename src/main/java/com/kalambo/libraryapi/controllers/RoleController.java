package com.kalambo.libraryapi.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.kalambo.libraryapi.dtos.RoleDto;
import com.kalambo.libraryapi.dtos.UpdatePermissionDto;
import com.kalambo.libraryapi.dtos.UpdateRoleDto;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IRole;
import com.kalambo.libraryapi.responses.IRoleV2;
import com.kalambo.libraryapi.services.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Role", description = "Manage user roles.")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    @Operation(summary = "Create a new role.", description = "Some description.")
    public ResponseEntity<IRole> createRole(@Valid @RequestBody RoleDto payload) {
        log.info("POST - /api/v1/roles");
        IRole createdRole = roleService.create(payload);

        return new ResponseEntity<IRole>(createdRole, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Retrieve all roles.", description = "Some description.")
    public ResponseEntity<IPage<IRoleV2>> getAllRoles(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET - /api/v1/roles");

        return ResponseEntity.ok(roleService.getAll(PageRequest.of(page, size)));
    }

    @GetMapping("{id}")
    @Operation(summary = "Retrieve a single role by id.", description = "Some description.")
    public ResponseEntity<IRole> getRoleById(@PathVariable("id") UUID roleId) {
        log.info("GET - /api/v1/roles/" + roleId);
        IRole role = roleService.getById(roleId);

        return ResponseEntity.ok(role);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update role details.", description = "Some description.")
    public ResponseEntity<IRole> updateRole(@PathVariable("id") UUID roleId,
            @RequestBody UpdateRoleDto payload) {
        log.info("PUT - /api/v1/roles/" + roleId);

        payload.setId(roleId);
        IRole updatedRole = roleService.update(payload);

        return ResponseEntity.ok(updatedRole);
    }

    @PutMapping("{id}/manage-permissions")
    @Operation(summary = "Manage role's permissions.", description = "Some description.")
    public ResponseEntity<IRole> managePermissions(@PathVariable("id") UUID roleId,
            @RequestBody UpdatePermissionDto payload) {
        log.info("PUT - /api/v1/roles/" + roleId + "/manage-permissions");

        return ResponseEntity.ok(roleService.managePermissions(payload.setRoleId(roleId)));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a single role by id.", description = "Some description.")
    public ResponseEntity<String> deleteRoleById(@PathVariable("id") UUID roleId) {
        log.warn("DELETE - /api/v1/roles/" + roleId);

        roleService.delete(roleId);
        String message = "Role with ID: " + roleId + ", deleted successful.";

        return ResponseEntity.ok(message);
    }
}
