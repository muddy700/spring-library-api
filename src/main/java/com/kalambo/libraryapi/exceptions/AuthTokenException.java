package com.kalambo.libraryapi.exceptions;

public class AuthTokenException extends RuntimeException {
    // private static final long serialVersionUID = -8790211652911971729L || 1L;

    public AuthTokenException(String message) {
        super(message);
    }
}
