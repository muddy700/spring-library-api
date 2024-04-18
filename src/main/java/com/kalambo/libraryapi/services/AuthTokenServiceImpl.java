package com.kalambo.libraryapi.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.entities.AuthToken;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.TokenTypeEnum;
import com.kalambo.libraryapi.exceptions.AuthTokenException;
import com.kalambo.libraryapi.repositories.AuthTokenRepository;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {
    private final long expirationTime = 12 * 60 * 60 * 1000;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Override
    public AuthToken create(User user, TokenTypeEnum type) {
        return authTokenRepository.save(getTokenPayload(user, type));
    }

    @Override
    public AuthToken isActive(String token) {
        // Check if the token exist in the db and has not expired
        AuthToken result = getByToken(token);
        return hasExpired(result) ? null : result;
    }

    @Override
    public AuthToken reCreate(String token) {
        AuthToken existingToken = getByToken(token);

        delete(existingToken);
        return authTokenRepository.save(getTokenPayload(existingToken.getUser(), existingToken.getType()));
    }

    @Override
    public void delete(AuthToken payload) {
        authTokenRepository.delete(payload);
    }

    private AuthToken getByToken(String token) throws AuthTokenException {
        return authTokenRepository.findByToken(token).orElseThrow(() -> new AuthTokenException("Invalid token"));
    }

    private Boolean isPresent(User user, TokenTypeEnum type) {
        return authTokenRepository.existsByUserAndType(user, type);
    }

    private Boolean hasExpired(AuthToken payload) throws AuthTokenException {
        if (payload.getExpiresAt().before(new Date()))
            throw new AuthTokenException("Token has expired");

        return false;
    }

    private final AuthToken getTokenPayload(User user, TokenTypeEnum type) throws AuthTokenException {
        if (isPresent(user, type))
            throw new AuthTokenException("Another token for this user and type already exist");

        final String token = UUID.randomUUID().toString();
        final Date expiryDate = new Date(System.currentTimeMillis() + expirationTime);

        return new AuthToken(token, expiryDate, type, user);
    }
}
