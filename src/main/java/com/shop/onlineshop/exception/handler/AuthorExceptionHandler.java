package com.shop.onlineshop.exception.handler;

import com.shop.onlineshop.exception.AuthorAlreadyExistException;
import com.shop.onlineshop.exception.AuthorNotFoundException;
import com.shop.onlineshop.exception.ErrorMessageDto;
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

    @ExceptionHandler(value = {AuthorAlreadyExistException.class})
    @ResponseBody
    public ResponseEntity<Object> handleAuthorAlreadyExistException (AuthorAlreadyExistException exception) {

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ErrorMessageDto(
                        exception.getHttpStatus().value(),
                        exception.getMessage()));
    }
}
