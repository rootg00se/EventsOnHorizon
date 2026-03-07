package com.deg00se.events.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);

        ErrorResponseDto errorDto = new ErrorResponseDto(
                "Internal server error",
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(EntityNotFoundException exception) {
        log.error(exception.getMessage(), exception);

        ErrorResponseDto errorDto = new ErrorResponseDto(
                "Not found exception",
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponseDto> handleIOException(IOException exception) {
        log.error(exception.getMessage(), exception);

        ErrorResponseDto errorDto = new ErrorResponseDto(
                "File upload failed",
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handeBadCredentialsException(BadCredentialsException exception) {
        log.error(exception.getMessage(), exception);

        ErrorResponseDto errorDto = new ErrorResponseDto(
                "Invalid username or password",
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethoArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);

        FieldError errorMessage = exception.getBindingResult().getFieldError();

        ErrorResponseDto errorDto = new ErrorResponseDto(
                "Bad request exception",
                errorMessage != null ? errorMessage.getDefaultMessage() : "Incorrect data",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(Exception exception) {
        log.error(exception.getMessage(), exception);

        ErrorResponseDto errorDto = new ErrorResponseDto(
                "Bad request exception",
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
}
