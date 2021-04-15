package com.shop.onlineshop.exception.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.shop.onlineshop.exception.CategoryAlreadyExistException;
import com.shop.onlineshop.exception.CategoryNotFountException;
import com.shop.onlineshop.exception.ErrorMessageDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CategoryExceptionHandlerTest {
    @Test
    public void testHandleCategoryNotFoundException() {
        CategoryExceptionHandler categoryExceptionHandler = new CategoryExceptionHandler();
        ResponseEntity<Object> actualHandleCategoryNotFoundExceptionResult = categoryExceptionHandler
                .handleCategoryNotFoundException(new CategoryNotFountException("An error occurred", HttpStatus.CONTINUE));
        assertTrue(actualHandleCategoryNotFoundExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleCategoryNotFoundExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleCategoryNotFoundExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleCategoryNotFoundExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleCategoryNotFoundException2() {
        CategoryExceptionHandler categoryExceptionHandler = new CategoryExceptionHandler();

        CategoryNotFountException categoryNotFountException = new CategoryNotFountException("An error occurred",
                HttpStatus.CONTINUE);
        categoryNotFountException.addSuppressed(new Throwable());
        ResponseEntity<Object> actualHandleCategoryNotFoundExceptionResult = categoryExceptionHandler
                .handleCategoryNotFoundException(categoryNotFountException);
        assertTrue(actualHandleCategoryNotFoundExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleCategoryNotFoundExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleCategoryNotFoundExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleCategoryNotFoundExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleCategoryAlreadyExistException() {
        CategoryExceptionHandler categoryExceptionHandler = new CategoryExceptionHandler();
        ResponseEntity<Object> actualHandleCategoryAlreadyExistExceptionResult = categoryExceptionHandler
                .handleCategoryAlreadyExistException(
                        new CategoryAlreadyExistException("An error occurred", HttpStatus.CONTINUE));
        assertTrue(actualHandleCategoryAlreadyExistExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleCategoryAlreadyExistExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleCategoryAlreadyExistExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleCategoryAlreadyExistExceptionResult.getBody()).getErrorMessage());
    }

    @Test
    public void testHandleCategoryAlreadyExistException2() {
        CategoryExceptionHandler categoryExceptionHandler = new CategoryExceptionHandler();

        CategoryAlreadyExistException categoryAlreadyExistException = new CategoryAlreadyExistException("An error occurred",
                HttpStatus.CONTINUE);
        categoryAlreadyExistException.addSuppressed(new Throwable());
        ResponseEntity<Object> actualHandleCategoryAlreadyExistExceptionResult = categoryExceptionHandler
                .handleCategoryAlreadyExistException(categoryAlreadyExistException);
        assertTrue(actualHandleCategoryAlreadyExistExceptionResult.hasBody());
        assertEquals(HttpStatus.CONTINUE, actualHandleCategoryAlreadyExistExceptionResult.getStatusCode());
        assertEquals(100, ((ErrorMessageDto) actualHandleCategoryAlreadyExistExceptionResult.getBody()).getErrorCode());
        assertEquals("An error occurred",
                ((ErrorMessageDto) actualHandleCategoryAlreadyExistExceptionResult.getBody()).getErrorMessage());
    }
}

