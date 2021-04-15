package com.shop.onlineshop.exception.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.shop.onlineshop.exception.AuthorAlreadyExistException;
import com.shop.onlineshop.exception.AuthorNotFoundException;
import com.shop.onlineshop.exception.ErrorMessageDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthorExceptionHandlerTest {
    @Test
    public void testHandleAuthorNotFoundException() {
        AuthorExceptionHandler authorExceptionHandler = new AuthorExceptionHandler();
        ResponseEntity<Object> actualHandleAuthorNotFoundExceptionResult = authorExceptionHandler
                .handleAuthorNotFoundException(new AuthorNotFoundException("An error occurred", HttpStatus.CONTINUE));
        assertTrue(actualHandleAuthorNotFoundExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleAuthorNotFoundExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleAuthorNotFoundExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleAuthorNotFoundExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleAuthorNotFoundException2() {
        AuthorExceptionHandler authorExceptionHandler = new AuthorExceptionHandler();

        AuthorNotFoundException authorNotFoundException = new AuthorNotFoundException("An error occurred",
                HttpStatus.CONTINUE);
        authorNotFoundException.addSuppressed(new Throwable());
        ResponseEntity<Object> actualHandleAuthorNotFoundExceptionResult = authorExceptionHandler
                .handleAuthorNotFoundException(authorNotFoundException);
        assertTrue(actualHandleAuthorNotFoundExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleAuthorNotFoundExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleAuthorNotFoundExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleAuthorNotFoundExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleAuthorAlreadyExistException() {
        AuthorExceptionHandler authorExceptionHandler = new AuthorExceptionHandler();
        ResponseEntity<Object> actualHandleAuthorAlreadyExistExceptionResult = authorExceptionHandler
                .handleAuthorAlreadyExistException(new AuthorAlreadyExistException("An error occurred", HttpStatus.CONTINUE));
        assertTrue(actualHandleAuthorAlreadyExistExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleAuthorAlreadyExistExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleAuthorAlreadyExistExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleAuthorAlreadyExistExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleAuthorAlreadyExistException2() {
        AuthorExceptionHandler authorExceptionHandler = new AuthorExceptionHandler();

        AuthorAlreadyExistException authorAlreadyExistException = new AuthorAlreadyExistException("An error occurred",
                HttpStatus.CONTINUE);
        authorAlreadyExistException.addSuppressed(new Throwable());
        ResponseEntity<Object> actualHandleAuthorAlreadyExistExceptionResult = authorExceptionHandler
                .handleAuthorAlreadyExistException(authorAlreadyExistException);
        assertTrue(actualHandleAuthorAlreadyExistExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleAuthorAlreadyExistExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleAuthorAlreadyExistExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleAuthorAlreadyExistExceptionResult.getBody()).getErrorMessage());
    }
}

