package com.kalambo.libraryapi.services;

import java.util.UUID;

import com.kalambo.libraryapi.dtos.ChangePasswordDto;
import com.kalambo.libraryapi.dtos.ForgotPasswordDto;
import com.kalambo.libraryapi.dtos.LoginDto;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.responses.ITokenVerification;
import com.kalambo.libraryapi.responses.IForgotPassword;
import com.kalambo.libraryapi.responses.ILogin;
import com.kalambo.libraryapi.responses.ISuccess;

public interface AuthService {
    ILogin authenticate(LoginDto payload);

    User getPrincipal();

    UUID getPrincipalId();

    String getPrincipalUsername();

    void changePassword(ChangePasswordDto payload);

    IForgotPassword forgotPassword(ForgotPasswordDto payload);

    ITokenVerification verifyAuthToken(String verificationToken);

    ITokenVerification verifyOtp(Integer verificationCode);

    ISuccess resendVerificationToken(String currentToken);

    ISuccess resendOtp(Integer currentCode);

    ISuccess verifyPhoneNumber();
}