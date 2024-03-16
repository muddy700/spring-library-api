package com.kalambo.libraryapi.seeders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.dtos.ActionDto;
import com.kalambo.libraryapi.dtos.PermissionDto;
import com.kalambo.libraryapi.entities.Permission;
import com.kalambo.libraryapi.repositories.PermissionRepository;
import com.kalambo.libraryapi.services.PermissionService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PermissionSeeder {
    /**
     * ! Use repositories only for 'Read' operations and
     * Services for other operations(Create, Update and Delete)
     */

    private Integer totalPermissionsCreated = 0;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionRepository permissionRepository;

    public void seed() {
        log.info("Starting Permissions seeding...");

        try {
            final List<ActionDto> actions = getAllowedActions();

            for (ActionDto action : actions) {
                action.getResources()
                        .forEach(resource -> savePermission(buildPermissionDto(resource, action.getName())));
            }

            if (totalPermissionsCreated > 0)
                log.info("Permissions seeding completed, " + totalPermissionsCreated + " Permission(s) added.");
            else
                log.info("Permissions seeding skipped, no new permission(s) to add.");
        } catch (Exception e) {
            log.error("Failed to seed Permissions: " + e.getMessage(), e);
            log.info(totalPermissionsCreated + " Permission(s) added.");
        }
    }

    private List<ActionDto> getAllowedActions() {
        List<ActionDto> actions = new ArrayList<ActionDto>();

        // Add resources/entities that need permission for 'Create' operation.
        String[] creatableResources = { "Task", "Role", "User" };

        // Add resources/entities that need permission for 'Read' operation.
        String[] viewableResources = { "Task", "Permission", "Role", "User" };

        // Add resources/entities that need permission for 'Update' operation.
        String[] updatebleResources = { "Task", "Role", "User" };

        // Add resources/entities that need permission for 'Delete' operation.
        String[] deletableResources = { "Task", "Role", "User" };

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
        ActionDto actionDto = new ActionDto().setName(name);
        return appendResources(actionDto, resources);
    }

    private ActionDto appendResources(ActionDto actionDto, String[] resources) {
        for (String resource : resources) {
            actionDto.addResource(resource);
        }

        return actionDto;
    }

    private PermissionDto buildPermissionDto(String resourceName, String action) {
        String description = "Can " + action + " " + resourceName + "(s).";

        PermissionDto payload = new PermissionDto().setResourceName(resourceName)
                .setAction(action + "_" + resourceName.toLowerCase()).setDescription(description);

        return payload;
    }

    private void savePermission(PermissionDto payload) {
        Optional<Permission> optionalPermission = permissionRepository.findByAction(payload.getAction());

        if (optionalPermission.isPresent())
            log.info("Permission with action: '" + payload.getAction() + "', already exist.");
        else {
            permissionService.create(payload);
            totalPermissionsCreated += 1;
        }
    }
}
