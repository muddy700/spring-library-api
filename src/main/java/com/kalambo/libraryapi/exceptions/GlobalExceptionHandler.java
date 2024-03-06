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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kalambo.libraryapi.responses.IError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // TODO: Remember to handle the following
    // ValidationException, SQLException, EntityNotFoundException,
    // NoSuchElementException, IllegalArgumentException, Duplicated Resource

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<IError> handleNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return finalResult(ex, request, HttpStatus.NOT_FOUND, "Resource not found");
    }

    // TODO: Review the description returned and modify to hide db info
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<IError> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {
        return finalResult(ex, request, HttpStatus.BAD_REQUEST, "Data integrity violation");
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
    public final ResponseEntity<IError> handleAllUncaughtExceptions(Exception ex, WebRequest request) {
        return finalResult(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occured");
    }

    private IError formatError(Exception ex, WebRequest request, String title) {
        IError errorDetails = new IError().setTitle(title)
                .setTimestamp(new Date()).setDescription(ex.getMessage())
                .setPath(request.getDescription(false).substring(4));

        log.error(title + " :: " + errorDetails.getDescription() + " ==> ", ex);
        return errorDetails;
    }

    private final ResponseEntity<IError> finalResult(IError responseBody, HttpStatus statusCode) {
        return ResponseEntity.status(statusCode).body(responseBody);
    }

    private final ResponseEntity<IError> finalResult(Exception ex, WebRequest request, HttpStatus statusCode,
            String title) {
        return finalResult(formatError(ex, request, title), statusCode);
    }
}
