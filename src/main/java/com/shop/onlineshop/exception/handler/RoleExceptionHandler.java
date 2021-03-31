package com.shop.onlineshop.exception.handler;

import com.shop.onlineshop.exception.ErrorMessageDto;
import com.shop.onlineshop.exception.InvalidRoleException;
import com.shop.onlineshop.exception.RoleAlreadyExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RoleExceptionHandler {

    @ExceptionHandler(value = {InvalidRoleException.class})
    @ResponseBody
    public ResponseEntity<Object> handleInvalidRoleException (InvalidRoleException exception) {

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ErrorMessageDto(exception.getHttpStatus().value(), exception.getMessage()));
    }

    @ExceptionHandler(value = {RoleAlreadyExistException.class})
    @ResponseBody
    public ResponseEntity<Object> handleRoleAlreadyExistException (RoleAlreadyExistException exception) {

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new ErrorMessageDto(exception.getHttpStatus().value(), exception.getMessage()));
    }
}
