package com.shop.onlineshop.exception.handler;

import com.shop.onlineshop.exception.BookAlreadyExistException;
import com.shop.onlineshop.exception.BookNotFoundException;
import com.shop.onlineshop.exception.ErrorMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(value = {BookNotFoundException.class})
    @ResponseBody
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException exception) {

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ErrorMessageDto(exception.getHttpStatus().value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {BookAlreadyExistException.class})
    @ResponseBody
    public ResponseEntity<Object> handleBookAlreadyExistException(BookAlreadyExistException exception) {

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ErrorMessageDto(exception.getHttpStatus().value(), exception.getMessage()));
    }
}
