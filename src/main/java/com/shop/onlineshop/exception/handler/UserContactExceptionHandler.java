package com.shop.onlineshop.exception.handler;

import com.shop.onlineshop.exception.ErrorMessageDto;
import com.shop.onlineshop.exception.UserContactNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class UserContactExceptionHandler {

    @ExceptionHandler(value = {UserContactNotFoundException.class})
    @ResponseBody
    public ResponseEntity<Object> handleUserContactNotFoundException (UserContactNotFoundException exception) {

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ErrorMessageDto(exception.getHttpStatus().value(), exception.getMessage()));
    }
}
