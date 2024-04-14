package com.kalambo.libraryapi.exceptions;

public class InvalidOldPasswordException extends RuntimeException {
    // private static final long serialVersionUID = -8790211652911971729L || 1L;

    public InvalidOldPasswordException(String message) {
        super(message);
    }
}
