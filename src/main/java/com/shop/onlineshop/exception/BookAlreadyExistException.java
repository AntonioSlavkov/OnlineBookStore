package com.shop.onlineshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class BookAlreadyExistException extends RuntimeException {

    private final HttpStatus httpStatus;

    public BookAlreadyExistException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
