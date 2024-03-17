package com.kalambo.libraryapi.seeders;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.dtos.RoleDto;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.repositories.RoleRepository;
import com.kalambo.libraryapi.services.RoleService;

import lombok.extern.slf4j.Slf4j;

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

    public void seed() {
        log.info("Starting Roles seeding...");

        try {
            saveRole(buildRoleDto("Admin"));
        } catch (Exception e) {
            log.error("Failed to seed Roles: " + e.getMessage(), e);
        }
    }

    private RoleDto buildRoleDto(String name) {
        String description = "Role for the overall system administrator who can perform anything.";

        return new RoleDto().setName(name).setDescription(description);
    }

    private void saveRole(RoleDto payload) {
        Optional<Role> optionalRole = roleRepository.findByName(payload.getName());

        if (optionalRole.isPresent()) {
            log.info("Role with name: '" + payload.getName() + "', already exist.");
            log.info("Roles seeding skipped, no new role(s) to add.");
        }
        else {
            roleService.create(payload);
            log.info("Roles seeding completed, 'Admin' Role added.");
        }
    }
}
