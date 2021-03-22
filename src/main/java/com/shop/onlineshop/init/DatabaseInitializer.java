package com.shop.onlineshop.init;

import com.shop.onlineshop.service.AuthorService;
import com.shop.onlineshop.service.BookService;
import com.shop.onlineshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {
        authorService.initAuthors();
        categoryService.initCategories();
        bookService.initBooks();
    }
}
