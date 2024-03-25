package com.kalambo.libraryapi.seeders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.dtos.RoleDto;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.repositories.PermissionRepository;
import com.kalambo.libraryapi.repositories.RoleRepository;
import com.kalambo.libraryapi.services.RoleService;

import lombok.extern.slf4j.Slf4j;

// TODO: Refactor this file to optimize queries
@Component
@Slf4j
public class RoleSeeder {
    /**
     * ! Use repositories only for 'Read' operations and
     * Services for other operations(Create, Update and Delete)
     */

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public void seed() {
        log.info("Starting Roles seeding...");

        try {
            saveRole(buildRoleDto("Admin"));
        } catch (Exception e) {
            log.error("Failed to seed Roles: " + e.getMessage(), e);
        }
    }

    private RoleDto buildRoleDto(String name) {
        List<UUID> permissionsIds = new ArrayList<UUID>();
        permissionRepository.findAll().forEach(permission -> permissionsIds.add(permission.getId()));

        String description = "Role for the overall system administrator who can perform anything.";
        return new RoleDto().setName(name).setDescription(description).setPermissionsIds(permissionsIds);
    }

    private void saveRole(RoleDto payload) {
        Optional<Role> optionalRole = roleRepository.findByName(payload.getName());

        if (optionalRole.isPresent()) {
            log.info("Role for System Administrators, already exist.");
            log.info("Roles seeding skipped, no new role(s) to add.");
        } else {
            roleService.create(payload);
            log.info("Roles seeding completed, a new role for System Administrators added.");
        }
    }
}
