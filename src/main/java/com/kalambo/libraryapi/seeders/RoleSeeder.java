package com.kalambo.libraryapi.seeders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.dtos.RoleDto;
import com.kalambo.libraryapi.entities.Permission;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.enums.RoleEnum;
import com.kalambo.libraryapi.repositories.PermissionRepository;
import com.kalambo.libraryapi.repositories.RoleRepository;
import com.kalambo.libraryapi.services.RoleService;
import com.kalambo.libraryapi.utilities.GlobalUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RoleSeeder {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    private List<Permission> allSystemPermisions, studentPermissions = new ArrayList<Permission>();

    private String adminRoleName, studentRoleName;

    public void seed() {
        log.info("Starting Roles seeding...");

        try {
            initializeVariables();
            prepareRolesDtos().forEach(roleDto -> attachPermissions(saveRole(roleDto)));

            log.info("Roles seeding completed.");
        } catch (Exception ex) {
            log.error("Failed to seed Roles: " + ex.getMessage(), ex);
        }
    }

    private void initializeVariables() {
        adminRoleName = capitalize(RoleEnum.ADMIN);
        studentRoleName = capitalize(RoleEnum.STUDENT);

        allSystemPermisions = permissionRepository.findAll();
        List<String> studentActions = Arrays.asList("view_book");

        for (Permission permission : allSystemPermisions) {
            if (studentActions.contains(permission.getAction()))
                studentPermissions.add(permission);
        }
    }

    private List<RoleDto> prepareRolesDtos() {
        List<RoleDto> dtos = new ArrayList<RoleDto>(2);

        // For the role: Admin
        dtos.add(new RoleDto().setName(adminRoleName)
                .setDescription("Role for the overall system administrator who can perform anything."));

        // For the role: Student
        dtos.add(new RoleDto().setName(studentRoleName).setDescription("Role for students."));

        return dtos;
    }

    private Role saveRole(RoleDto payload) {
        Optional<Role> optionalRole = roleRepository.findByName(payload.getName());

        if (optionalRole.isPresent())
            return optionalRole.get();
        else
            return roleService.getEntity(roleService.create(payload));
    }

    private void attachPermissions(Role role) {
        if (role.getName().equals(adminRoleName))
            role.addPermissions(removeDuplicates(allSystemPermisions, role));

        else if (role.getName().equals(studentRoleName))
            role.addPermissions(removeDuplicates(studentPermissions, role));

        roleRepository.save(role);
    }

    private List<Permission> removeDuplicates(List<Permission> permissionsPayload, Role role) {
        permissionsPayload.removeIf(payload -> role.getPermissions().contains(payload));

        return permissionsPayload;
    }

    private String capitalize(RoleEnum enumValue) {
        return GlobalUtil.capitalizeFirstLetter(enumValue.name());
    }
}
