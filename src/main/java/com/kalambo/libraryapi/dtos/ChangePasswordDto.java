package com.kalambo.libraryapi.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class ChangePasswordDto {
    @NotBlank(message = "Old password is required")
    private String oldPassword;

    // TODO: Add pattern validator for password
    @NotBlank(message = "New password is required")
    private String newPassword;
}
