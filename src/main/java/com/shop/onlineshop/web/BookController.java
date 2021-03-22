package com.shop.onlineshop.web;

import com.shop.onlineshop.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@CrossOrigin("*")
public class BookController {

    private final BookService bookService;


}
