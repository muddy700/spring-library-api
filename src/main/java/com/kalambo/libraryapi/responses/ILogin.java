package com.kalambo.libraryapi.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ILogin {
    private String token;
    private long expiresIn;

    private IUser user;
}
