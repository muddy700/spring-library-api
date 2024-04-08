package com.kalambo.libraryapi.seeders;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.dtos.UserDto;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.enums.RoleEnum;
import com.kalambo.libraryapi.repositories.RoleRepository;
import com.kalambo.libraryapi.repositories.UserRepository;
import com.kalambo.libraryapi.services.UserService;
import com.kalambo.libraryapi.utilities.GlobalUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserSeeder {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void seed() {
        log.info("Starting Users seeding...");

        try {
            UUID roleId = getRoleId(capitalize(RoleEnum.ADMIN));

            if (roleId != null)
                saveUser(buildUserDto(roleId));
            else
                log.info("Users seeding skipped, Administrator Role was not found.");

        } catch (Exception ex) {
            log.error("Failed to seed Users: " + ex.getMessage(), ex);
        }
    }

    private UserDto buildUserDto(UUID roleId) {
        // Details for the System Admin.
        return new UserDto().setEmail("mohamedmfaume700@gmail.com").setRoleId(roleId)
                .setFullName("Mohamed Mfaume Mohamed").setPhoneNumber("255717963697").setGender("M");
    }

    private void saveUser(UserDto payload) {
        if (userRepository.findByEmail(payload.getEmail()).isPresent())
            log.info("Users seeding skipped, no new user(s) to add.");
        else {
            userService.create(payload);
            log.info("Users seeding completed, System Admin added.");
        }
    }

    private UUID getRoleId(String roleName) {
        Optional<Role> optionalRole = roleRepository.findByName(roleName);

        if (optionalRole.isPresent())
            return optionalRole.get().getId();
        else
            return null;
    }

    private String capitalize(RoleEnum enumValue) {
        return GlobalUtil.capitalizeFirstLetter(enumValue.name());
    }
}
