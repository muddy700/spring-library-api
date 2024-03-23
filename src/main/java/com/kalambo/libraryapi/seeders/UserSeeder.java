package com.kalambo.libraryapi.seeders;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.dtos.UserDto;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.repositories.RoleRepository;
import com.kalambo.libraryapi.repositories.UserRepository;
import com.kalambo.libraryapi.services.UserService;

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
            UUID roleId = getRoleId("Admin");

            if (roleId != null)
                saveUser(buildUserDto(roleId));
            else
                log.info("Users seeding skipped, Administrator Role was not found.");

        } catch (Exception e) {
            log.error("Failed to seed Users: " + e.getMessage(), e);
        }
    }

    private UserDto buildUserDto(UUID roleId) {
        // Details for System Admin.
        String email = "mohamedmfaume700@gmail.com";
        String name = "Mohamed Mfaume Mohamed";
        String phone = "255717963697";

        UserDto payload = new UserDto().setEmail(email).setRoleId(roleId)
                .setFullName(name).setPhoneNumber(phone).setGender("M");

        return payload;
    }

    private void saveUser(UserDto payload) {
        Optional<User> optionalUser = userRepository.findByEmail(payload.getEmail());

        if (optionalUser.isPresent()) {
            log.info("User with admin details, already exist.");
            log.info("Users seeding skipped, no new user(s) to add.");
        } else {
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
}
