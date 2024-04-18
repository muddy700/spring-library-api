package com.kalambo.libraryapi.services;

import com.kalambo.libraryapi.entities.AuthToken;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.TokenTypeEnum;

public interface AuthTokenService {
    AuthToken create(User user, TokenTypeEnum type);

    AuthToken isActive(String token);

    AuthToken reCreate(String token);

    void delete(AuthToken payload);
}
