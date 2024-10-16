package com.kalambo.libraryapi.dtos;

import java.util.Date;
import java.util.UUID;

import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.GenderEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class UserDto {
    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 40)
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^255(\\d{9})$", message = "Phone number must start with 255 and followed by 9 digits")
    private String phoneNumber;

    @NotNull(message = "Role ID is required")
    private UUID roleId;

    @NotBlank(message = "Full name is required")
    @Size(min = 10, max = 50)
    private String fullName;

    @NotNull(message = "Gender is required")
    private GenderEnum gender;

    public User toEntity(Role role, String password) {
        return new User().setRole(role).setPassword(password).setPasswordChangedAt(new Date())
                .setFullName(fullName).setGender(gender).setEmail(email).setPhoneNumber(phoneNumber);
    }
}
