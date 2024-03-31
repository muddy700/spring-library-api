package com.kalambo.libraryapi.exceptions;

import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kalambo.libraryapi.responses.IError;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@ControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // TODO: Remember to handle the following
    // SQLException, EntityNotFoundException NoSuchElementException,
    // IllegalArgumentException => for null permission-ids

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final IError handleNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return formatError(ex, request, "Resource not found");
    }

    @ExceptionHandler(ResourceDuplicationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final IError onDuplicationException(ResourceDuplicationException ex, WebRequest request) {
        return formatError(ex, request, "Resource duplication");
    }

    @ExceptionHandler(ExternalAPIException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final IError handleExternalAPIException(ExternalAPIException ex, WebRequest request) {
        return formatError(ex, request, "Failed to call external api");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final IError handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        IError errorInfo = formatError(ex, request, "Constraint violation")
                .setDescription("Make some changes in the field(s) listed below and try again");

        // TODO: Find a better way to extract field-name from the string
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            errorInfo.addValidationError(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return errorInfo;
    }

    // TODO: Review the description returned and modify to hide db info
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final IError handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        return formatError(ex, request, "Data integrity violation");
    }

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        IError errorInfo = formatError(ex, request, "Input(s) validation failed")
                .setDescription("Make some changes in the field(s) listed below and try again");

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorInfo.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errorInfo, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final IError handleAllUncaughtExceptions(Exception ex, WebRequest request) {
        return formatError(ex, request, "Unknown error occured");
    }

    private IError formatError(Exception ex, WebRequest request, String title) {
        IError errorDetails = new IError().setTitle(title)
                .setTimestamp(new Date()).setDescription(ex.getMessage())
                .setPath(request.getDescription(false).substring(4));

        if (title.equals("Unknown error occured"))
            log.error(title + " :: " + errorDetails.getDescription() + " ==> ", ex);
        else
            log.error(title + " :: " + errorDetails.getDescription());

        return errorDetails;
    }
}
