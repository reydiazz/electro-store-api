package com.electro.store.api.shared.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handle(BusinessException exception) {
        ErrorResponse response = new ErrorResponse(
                exception.getHttpStatus().value(),
                exception.getCode(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }

}
