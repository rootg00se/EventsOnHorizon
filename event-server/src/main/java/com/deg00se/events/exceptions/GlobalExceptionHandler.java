package com.deg00se.events.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
//        log.error(exception.getMessage(), exception);
//
//        ErrorResponseDto errorDto = new ErrorResponseDto(
//                "Internal server error",
//                exception.getMessage(),
//                LocalDateTime.now(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value()
//        );
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
//    }
}
