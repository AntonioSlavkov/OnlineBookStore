package com.shop.onlineshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorAlreadyExistException extends RuntimeException{

    private final HttpStatus httpStatus;

    public AuthorAlreadyExistException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
