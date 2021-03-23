package com.shop.onlineshop.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BookExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BookNotFoundException.class})
    @ResponseBody
    public ResponseEntity<Object> handleBookNotFoundException (BookNotFoundException e) {

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorMessageDto(e.getHttpStatus().value(), e.getMessage()));
    }
}
