package com.kalambo.libraryapi.services;

import java.util.UUID;

import com.kalambo.libraryapi.dtos.ChangePasswordDto;
import com.kalambo.libraryapi.dtos.ForgotPasswordDto;
import com.kalambo.libraryapi.dtos.LoginDto;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.responses.ITokenVerification;
import com.kalambo.libraryapi.responses.IForgotPassword;
import com.kalambo.libraryapi.responses.ILogin;

public interface AuthService {
    ILogin authenticate(LoginDto payload);

    User getPrincipal();

    UUID getPrincipalId();

    String getPrincipalUsername();

    void changePassword(ChangePasswordDto payload);

    IForgotPassword forgotPassword(ForgotPasswordDto payload);

    ITokenVerification verifyEmail(String verificationToken);

    ITokenVerification verifyPasswordResetToken(String verificationToken);
}