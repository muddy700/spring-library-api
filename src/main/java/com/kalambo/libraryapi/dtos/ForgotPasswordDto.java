package com.kalambo.libraryapi.dtos;

import com.kalambo.libraryapi.enums.CommunicationChannelEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class ForgotPasswordDto {
    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 40)
    @Email(message = "Enter a valid email")
    private String email;

    private CommunicationChannelEnum channel = CommunicationChannelEnum.EMAIL;
}
