package com.shop.onlineshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CategoryAlreadyExistException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CategoryAlreadyExistException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
