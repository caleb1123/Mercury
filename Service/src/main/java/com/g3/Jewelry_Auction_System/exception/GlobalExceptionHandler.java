package com.g3.Jewelry_Auction_System.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String> handlingRuntimeException(RuntimeException runtimeException) {
        return ResponseEntity.badRequest().body(runtimeException.getMessage());
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<String> handleAppException(AppException appException) {
        return ResponseEntity
                .status(appException.getErrorCode().getStatusCode().value())
                .body(appException.getErrorCode().getMessage());
    }
}
