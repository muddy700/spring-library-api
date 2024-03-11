package com.kalambo.libraryapi.exceptions;

public class ExternalAPIException extends RuntimeException {
    // private static final long serialVersionUID = -8790211652911971729L || 1L;

    public ExternalAPIException(String message) {
        super(message);
    }
}
