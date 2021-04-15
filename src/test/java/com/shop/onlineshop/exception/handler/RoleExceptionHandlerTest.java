package com.shop.onlineshop.exception.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.shop.onlineshop.exception.ErrorMessageDto;
import com.shop.onlineshop.exception.InvalidRoleException;
import com.shop.onlineshop.exception.RoleAlreadyExistException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RoleExceptionHandlerTest {
    @Test
    public void testHandleInvalidRoleException() {
        RoleExceptionHandler roleExceptionHandler = new RoleExceptionHandler();
        ResponseEntity<Object> actualHandleInvalidRoleExceptionResult = roleExceptionHandler
                .handleInvalidRoleException(new InvalidRoleException("An error occurred", HttpStatus.CONTINUE));
        assertTrue(actualHandleInvalidRoleExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleInvalidRoleExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleInvalidRoleExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleInvalidRoleExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleInvalidRoleException2() {
        RoleExceptionHandler roleExceptionHandler = new RoleExceptionHandler();

        InvalidRoleException invalidRoleException = new InvalidRoleException("An error occurred", HttpStatus.CONTINUE);
        invalidRoleException.addSuppressed(new Throwable());
        ResponseEntity<Object> actualHandleInvalidRoleExceptionResult = roleExceptionHandler
                .handleInvalidRoleException(invalidRoleException);
        assertTrue(actualHandleInvalidRoleExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleInvalidRoleExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleInvalidRoleExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleInvalidRoleExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleRoleAlreadyExistException() {
        RoleExceptionHandler roleExceptionHandler = new RoleExceptionHandler();
        ResponseEntity<Object> actualHandleRoleAlreadyExistExceptionResult = roleExceptionHandler
                .handleRoleAlreadyExistException(new RoleAlreadyExistException("An error occurred", HttpStatus.CONTINUE));
        assertTrue(actualHandleRoleAlreadyExistExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleRoleAlreadyExistExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleRoleAlreadyExistExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleRoleAlreadyExistExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleRoleAlreadyExistException2() {
        RoleExceptionHandler roleExceptionHandler = new RoleExceptionHandler();

        RoleAlreadyExistException roleAlreadyExistException = new RoleAlreadyExistException("An error occurred",
                HttpStatus.CONTINUE);
        roleAlreadyExistException.addSuppressed(new Throwable());
        ResponseEntity<Object> actualHandleRoleAlreadyExistExceptionResult = roleExceptionHandler
                .handleRoleAlreadyExistException(roleAlreadyExistException);
        assertTrue(actualHandleRoleAlreadyExistExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleRoleAlreadyExistExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleRoleAlreadyExistExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleRoleAlreadyExistExceptionResult.getBody()).getErrorMessage());
    }
}

