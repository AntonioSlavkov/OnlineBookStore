package com.shop.onlineshop.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AuthorExceptionHandler {

    @ExceptionHandler(value = {AuthorNotFoundException.class})
    @ResponseBody
    public ResponseEntity<Object> handleAuthorNotFoundException (AuthorNotFoundException exception) {

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ErrorMessageDto(
                        exception.getHttpStatus().value(),
                        exception.getMessage()));
    }
}
