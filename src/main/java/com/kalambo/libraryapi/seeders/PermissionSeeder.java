package com.kalambo.libraryapi.seeders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.dtos.ActionDto;
import com.kalambo.libraryapi.dtos.PermissionDto;
import com.kalambo.libraryapi.repositories.PermissionRepository;
import com.kalambo.libraryapi.services.PermissionService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PermissionSeeder {
    private Integer totalPermissionsCreated = 0;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionRepository permissionRepository;

    public void seed() {
        log.info("Starting Permissions seeding...");

        try {
            // Seed auto-configured permissions
            for (ActionDto action : getAllowedActions()) {
                action.getResources()
                        .forEach(resource -> savePermission(buildPermissionDto(resource, action.getName())));
            }

            // Seed special permissions
            getSpecialPermissions().forEach(permissionDto -> savePermission(permissionDto));

            if (totalPermissionsCreated > 0)
                log.info("Permissions seeding completed, " + totalPermissionsCreated + " Permission(s) added.");
            else
                log.info("Permissions seeding skipped, no new permission(s) to add.");
        } catch (Exception ex) {
            log.error("Failed to seed Permissions: " + ex.getMessage(), ex);
            log.info(totalPermissionsCreated + " Permission(s) added.");
        }
    }

    private List<ActionDto> getAllowedActions() {
        List<ActionDto> actions = new ArrayList<ActionDto>();

        // Add resources/entities that need permission for 'Create' operation.
        String[] creatableResources = { "Role", "User", "Book" };

        // Add resources/entities that need permission for 'Read' operation.
        String[] viewableResources = { "Permission", "Role", "User", "AuditTrail", "Book" };

        // Add resources/entities that need permission for 'Update' operation.
        String[] updatebleResources = { "Role", "User", "Book" };

        // Add resources/entities that need permission for 'Delete' operation.
        String[] deletableResources = { "Role", "User", "Book" };

        // Link CRUD operations with their applicable resources, as listed above.
        // For 'Create' operation.
        actions.add(buildActionDto("create", creatableResources));

        // For 'Read' operation.
        actions.add(buildActionDto("view", viewableResources));

        // For 'Update' operation.
        actions.add(buildActionDto("update", updatebleResources));

        // For 'Delete' operation.
        actions.add(buildActionDto("delete", deletableResources));

        return actions;
    }

    private ActionDto buildActionDto(String name, String[] resources) {
        return new ActionDto(name, Arrays.asList(resources));
    }

    private PermissionDto buildPermissionDto(String resourceName, String action) {
        return new PermissionDto().setResourceName(resourceName)
                .setAction(action + "_" + resourceName.toLowerCase())
                .setDescription("Can " + action + " " + resourceName + "(s).");
    }

    private void savePermission(PermissionDto payload) {
        if (!permissionRepository.findByAction(payload.getAction()).isPresent()) {
            permissionService.create(payload);
            totalPermissionsCreated += 1;
        }
    }

    private List<PermissionDto> getSpecialPermissions() {
        List<PermissionDto> dtos = new ArrayList<PermissionDto>();

        dtos.add(new PermissionDto().setAction("manage_permission")
                .setResourceName("Role").setDescription("Can add and remove  permission(s) to and from the role"));

        return dtos;
    }
}
