package com.shop.onlineshop.exception.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.shop.onlineshop.exception.ErrorMessageDto;
import com.shop.onlineshop.exception.UserContactNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserContactExceptionHandlerTest {
    @Test
    public void testHandleUserContactNotFoundException() {
        UserContactExceptionHandler userContactExceptionHandler = new UserContactExceptionHandler();
        ResponseEntity<Object> actualHandleUserContactNotFoundExceptionResult = userContactExceptionHandler
                .handleUserContactNotFoundException(new UserContactNotFoundException("An error occurred", HttpStatus.CONTINUE));
        assertTrue(actualHandleUserContactNotFoundExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleUserContactNotFoundExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleUserContactNotFoundExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleUserContactNotFoundExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleUserContactNotFoundException2() {
        UserContactExceptionHandler userContactExceptionHandler = new UserContactExceptionHandler();

        UserContactNotFoundException userContactNotFoundException = new UserContactNotFoundException("An error occurred",
                HttpStatus.CONTINUE);
        userContactNotFoundException.addSuppressed(new Throwable());
        ResponseEntity<Object> actualHandleUserContactNotFoundExceptionResult = userContactExceptionHandler
                .handleUserContactNotFoundException(userContactNotFoundException);
        assertTrue(actualHandleUserContactNotFoundExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleUserContactNotFoundExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleUserContactNotFoundExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleUserContactNotFoundExceptionResult.getBody()).getErrorMessage());
    }
}

