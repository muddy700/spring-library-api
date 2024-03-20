package com.kalambo.libraryapi.seeders;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.dtos.UserDto;
import com.kalambo.libraryapi.entities.User;
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

    public void seed() {
        log.info("Starting Users seeding...");

        try {
            saveUser(buildUserDto());
        } catch (Exception e) {
            log.error("Failed to seed Users: " + e.getMessage(), e);
        }
    }

    private UserDto buildUserDto() {
        // Details for System Admin.
        String email = "mohamedmfaume700@gmail.com";
        String name = "Mohamed Mfaume Mohamed";
        String phone = "255717963697";

        UserDto payload = new UserDto().setEmail(email)
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
}
