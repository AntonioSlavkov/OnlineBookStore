package com.shop.onlineshop.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CategoryExceptionHandler {

    @ExceptionHandler(value = {CategoryNotFountException.class})
    @ResponseBody
    public ResponseEntity<Object> handleCategoryNotFoundException (CategoryNotFountException exception) {

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ErrorMessageDto(
                        exception.getHttpStatus().value(),
                        exception.getMessage()));
    }
}
