package com.kalambo.libraryapi.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import com.kalambo.libraryapi.responses.ISuccess;
import com.kalambo.libraryapi.services.RoleService;
import com.kalambo.libraryapi.utilities.GlobalUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Role", description = "Manage user roles.")

public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    @Operation(summary = "Create a new role.", description = "Some description.")
    public ISuccess createRole(@Valid @RequestBody RoleDto payload) {
        logRequest("POST", "");

        return successResponse("create", roleService.create(payload));
    }

    @GetMapping
    @Operation(summary = "Retrieve all roles.", description = "Some description.")
    public IPage<IRoleV2> getAllRoles(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logRequest("GET", "");

        return roleService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    @Operation(summary = "Retrieve a single role by id.", description = "Some description.")
    public IRole getRoleById(@PathVariable("id") UUID roleId) {
        logRequest("GET", "/" + roleId);

        return roleService.getById(roleId);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update role details.", description = "Some description.")
    public ISuccess updateRole(@PathVariable("id") UUID roleId, @RequestBody UpdateRoleDto payload) {
        logRequest("PUT", "/" + roleId);

        roleService.update(payload.setId(roleId));
        return successResponse("update", roleId);
    }

    @PutMapping("{id}/manage-permissions")
    @Operation(summary = "Manage role's permissions.", description = "Some description.")
    public ISuccess managePermissions(@PathVariable("id") UUID roleId, @RequestBody UpdatePermissionDto payload) {
        logRequest("PUT", "/" + roleId + "/manage-permissions");

        roleService.managePermissions(payload.setRoleId(roleId));
        return successResponse("Role's permissions updated", roleId);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a single role by id.", description = "Some description.")
    public ISuccess deleteRoleById(@PathVariable("id") UUID roleId) {
        logRequest("DELETE", "/" + roleId);

        roleService.delete(roleId);
        return successResponse("delete", roleId);
    }

    private final ISuccess successResponse(String action, UUID resourceId) {
        return GlobalUtil.formatResponse("Role", action, resourceId);
    }

    private void logRequest(String httpMethod, String endpoint) {
        GlobalUtil.logRequest(httpMethod, "roles" + endpoint);
    }
}
