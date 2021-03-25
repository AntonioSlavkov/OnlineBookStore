package com.shop.onlineshop.exception.handler;

import com.shop.onlineshop.exception.CategoryAlreadyExistException;
import com.shop.onlineshop.exception.CategoryNotFountException;
import com.shop.onlineshop.exception.ErrorMessageDto;
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

    @ExceptionHandler(value = {CategoryAlreadyExistException.class})
    @ResponseBody
    public ResponseEntity<Object> handleCategoryAlreadyExistException (CategoryAlreadyExistException exception) {

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ErrorMessageDto(
                        exception.getHttpStatus().value(),
                        exception.getMessage()));
    }
}
