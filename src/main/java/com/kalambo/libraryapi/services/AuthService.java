package com.kalambo.libraryapi.services;

import com.kalambo.libraryapi.dtos.LoginDto;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.responses.ILogin;

public interface AuthService {
    ILogin authenticate(LoginDto payload);

    User getPrincipal();

    String getPrincipalUsername();
}