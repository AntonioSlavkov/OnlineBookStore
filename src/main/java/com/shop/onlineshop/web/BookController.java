package com.shop.onlineshop.web;

import com.shop.onlineshop.model.binding.BookAddBindingModel;
import com.shop.onlineshop.model.binding.BookUpdateBindingModel;
import com.shop.onlineshop.model.message.MessageDto;
import com.shop.onlineshop.model.view.BookViewModel;
import com.shop.onlineshop.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/books")
@AllArgsConstructor
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
    public ResponseEntity<Object> addBook (@Valid @RequestBody BookAddBindingModel bookAddBindingModel) {

        if (bookService.existsByBookTitle(bookAddBindingModel.getTitle())) {
            return ResponseEntity.status(403).body(
                    new MessageDto("Book with title " + bookAddBindingModel.getTitle() + " already exists."));
        }

        bookService.addBook(bookAddBindingModel);
        return ResponseEntity.ok().body(new MessageDto("Book added successfully."));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBook (@PathVariable Long id) {

        bookService.deleteBookById(id);
        return ResponseEntity.ok().body(new MessageDto("Book deleted successfully."));
    }

//    @PutMapping("book/{id}")
//    public ResponseEntity<BookViewModel> updateBook (@PathVariable Long id,
//                                                    @Valid @RequestBody BookUpdateBindingModel bookUpdateBindingModel) {
//
//        bookService.updateBook(id, bookUpdateBindingModel);
//        return ResponseEntity.ok().build();
//
//    }

}
