package com.kalambo.libraryapi.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class ITokenVerification {
    private String message;
    private String email;

    private String token;
    private long expiresIn;

    public ITokenVerification(String msg, String authToken) {
        message = msg;
        token = authToken;
    }
}
