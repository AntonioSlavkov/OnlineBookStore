package com.shop.onlineshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookNotFoundException extends RuntimeException{

    private final HttpStatus httpStatus;

    public BookNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
