package com.shop.onlineshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidRoleException extends RuntimeException {

    private final HttpStatus httpStatus;

    public InvalidRoleException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
