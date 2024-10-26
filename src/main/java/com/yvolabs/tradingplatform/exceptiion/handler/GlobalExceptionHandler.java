package com.yvolabs.tradingplatform.exceptiion.handler;

import jakarta.persistence.EntityExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 24/10/2024
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Set<String> errors = new HashSet<>();
        e.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BAD_REQUEST)
                        .validationErrors(errors)
                        .build()
                );
    }


    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExceptionResponse> handleEntityExistsException(EntityExistsException e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BAD_REQUEST)
                        .errors(Map.of("message", e.getMessage()))
                        .build()
                );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(NOT_FOUND)
                        .errors(Map.of("message", e.getMessage()))
                        .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(UNAUTHORIZED)
                        .errors(Map.of("message", e.getMessage()))
                        .build()
                );
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<ExceptionResponse> handleMailSendException(MailSendException e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BAD_REQUEST)
                        .errors(Map.of("message", Objects.requireNonNull(e.getMessage())))
                        .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllUnHandledExceptions(Exception e) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(INTERNAL_SERVER_ERROR)
                        .errors(Map.of("message", e.getMessage()))
                        .build()
                );

    }
}
