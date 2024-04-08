package com.kalambo.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.LoginDto;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.mappers.UserMapper;
import com.kalambo.libraryapi.responses.ILogin;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authManager;

    public ILogin authenticate(LoginDto payload) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(payload.getEmail(), payload.getPassword()));

        User user = (User) auth.getPrincipal();
        String authToken = jwtService.generateToken(user.getUsername());
        long expirationTime = jwtService.getExpirationTime();

        return new ILogin(authToken, expirationTime, userMapper.map(user));
    }

    @Override
    public User getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken)
            throw new AccessDeniedException("Request not authenticated");

        return (User) auth.getPrincipal();
    }

    @Override
    public String getPrincipalUsername() {
        return getPrincipal().getUsername();
    }
}
