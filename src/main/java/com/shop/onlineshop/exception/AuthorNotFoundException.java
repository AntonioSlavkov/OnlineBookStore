package com.shop.onlineshop.exception;

import java.util.function.Supplier;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String message) {
        super(message);
    }

}
