package com.muhkeun.productmanagerapi.exception;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Error> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = extractValidationErrorMessage(ex);
        return new ResponseEntity<>(new Error(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Error> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        String message = "잘못된 요청입니다.";

        if (!ex.getAllErrors().isEmpty()) {
            message = ex.getAllErrors().get(0).getDefaultMessage();
        }

        log.error(message);
        return new ResponseEntity<>(new Error(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Error> handleBindException(BindException ex) {
        String message = extractValidationErrorMessage(ex);
        return new ResponseEntity<>(new Error(message), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new Error("서버 오류가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private String extractValidationErrorMessage(BindException ex) {
        return Optional.ofNullable(ex.getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(
                        ex.getBindingResult().getAllErrors().stream().findFirst()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage).orElse("잘못된 요청입니다.")
                );
    }

    public record Error(String errorMessage) {
    }
}
