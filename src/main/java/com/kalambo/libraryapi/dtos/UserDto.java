package com.kalambo.libraryapi.dtos;

import java.util.UUID;

import com.kalambo.libraryapi.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 40)
    @Email(message = "Enter a valid email")
    private String email;

    // TODO: Add pattern validator for phone numbers ie.. 255xxxxxxxxx
    @NotBlank(message = "Phone number is required")
    @Size(min = 12, max = 12)
    private String phoneNumber;

    @NotNull(message = "Role ID is required")
    private UUID roleId;

    @NotBlank(message = "Full name is required")
    @Size(min = 10, max = 50)
    private String fullName;

    @NotBlank(message = "Gender is required")
    @Size(min = 1, max = 1)
    private String gender;

    public User toEntity() {
        return new User()
                .setFullName(fullName).setGender(gender)
                .setEmail(email).setPhoneNumber(phoneNumber);
    }
}
