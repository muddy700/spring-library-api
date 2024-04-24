package com.kalambo.libraryapi.exceptions;

public class InvalidPasswordException extends RuntimeException {
    // private static final long serialVersionUID = -8790211652911971729L || 1L;

    public InvalidPasswordException(String message) {
        super(message);
    }
}
