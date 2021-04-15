package com.shop.onlineshop.exception.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.shop.onlineshop.exception.BookAlreadyExistException;
import com.shop.onlineshop.exception.BookNotFoundException;
import com.shop.onlineshop.exception.ErrorMessageDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BookExceptionHandlerTest {
    @Test
    public void testHandleBookNotFoundException() {
        BookExceptionHandler bookExceptionHandler = new BookExceptionHandler();
        ResponseEntity<Object> actualHandleBookNotFoundExceptionResult = bookExceptionHandler
                .handleBookNotFoundException(new BookNotFoundException("An error occurred", HttpStatus.CONTINUE));
        assertTrue(actualHandleBookNotFoundExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleBookNotFoundExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleBookNotFoundExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleBookNotFoundExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleBookNotFoundException2() {
        BookExceptionHandler bookExceptionHandler = new BookExceptionHandler();

        BookNotFoundException bookNotFoundException = new BookNotFoundException("An error occurred", HttpStatus.CONTINUE);
        bookNotFoundException.addSuppressed(new Throwable());
        ResponseEntity<Object> actualHandleBookNotFoundExceptionResult = bookExceptionHandler
                .handleBookNotFoundException(bookNotFoundException);
        assertTrue(actualHandleBookNotFoundExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleBookNotFoundExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleBookNotFoundExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleBookNotFoundExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleBookAlreadyExistException() {
        BookExceptionHandler bookExceptionHandler = new BookExceptionHandler();
        ResponseEntity<Object> actualHandleBookAlreadyExistExceptionResult = bookExceptionHandler
                .handleBookAlreadyExistException(new BookAlreadyExistException("An error occurred", HttpStatus.CONTINUE));
        assertTrue(actualHandleBookAlreadyExistExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleBookAlreadyExistExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleBookAlreadyExistExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleBookAlreadyExistExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleBookAlreadyExistException2() {
        BookExceptionHandler bookExceptionHandler = new BookExceptionHandler();

        BookAlreadyExistException bookAlreadyExistException = new BookAlreadyExistException("An error occurred",
                HttpStatus.CONTINUE);
        bookAlreadyExistException.addSuppressed(new Throwable());
        ResponseEntity<Object> actualHandleBookAlreadyExistExceptionResult = bookExceptionHandler
                .handleBookAlreadyExistException(bookAlreadyExistException);
        assertTrue(actualHandleBookAlreadyExistExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleBookAlreadyExistExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleBookAlreadyExistExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleBookAlreadyExistExceptionResult.getBody()).getErrorMessage());
    }
}

