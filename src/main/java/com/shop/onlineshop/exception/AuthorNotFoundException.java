package com.shop.onlineshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AuthorNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
