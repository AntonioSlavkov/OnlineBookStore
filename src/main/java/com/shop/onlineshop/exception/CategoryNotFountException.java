package com.shop.onlineshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CategoryNotFountException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CategoryNotFountException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
