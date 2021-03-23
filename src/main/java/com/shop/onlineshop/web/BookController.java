package com.shop.onlineshop.web;

import com.shop.onlineshop.model.binding.BookAddBindingModel;
import com.shop.onlineshop.model.view.BookViewModel;
import com.shop.onlineshop.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@CrossOrigin("*")
public class BookController {

    private final BookService bookService;

    @GetMapping("/all")
    public List<BookViewModel> getAllBook() {

        return bookService.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public BookViewModel getBookById(@PathVariable long id) {

        return bookService.getBookById(id);
    }

    @PostMapping("/add")
    public void addBook (@Valid @RequestBody BookAddBindingModel bookAddBindingModel) {

        bookService.addBook(bookAddBindingModel);
    }
}
