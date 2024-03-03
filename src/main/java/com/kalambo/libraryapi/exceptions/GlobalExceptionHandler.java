package com.kalambo.libraryapi.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.kalambo.libraryapi.responses.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    // TODO: Handle ==> @ExceptionHandler(Exception.class)
    // TODO: Handle ==> NoSuchElementException
    // TODO: Handle ==> IllegalArgumentException

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(ResourceNotFoundException ex, WebRequest request) {

        return new ResponseEntity<ErrorResponse>(getErrorObject(ex, request), HttpStatus.NOT_FOUND);
    }

    public ErrorResponse getErrorObject(Exception ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse()
                .setTime(new Date()).setMessage(ex.getMessage())
                .setUrl(request.getDescription(false).substring(4));

        log.error(errorDetails.getMessage());
        return errorDetails;
    }
}
