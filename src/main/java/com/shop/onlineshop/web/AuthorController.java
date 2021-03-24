package com.shop.onlineshop.web;

import com.shop.onlineshop.model.binding.AuthorAddBindingModel;
import com.shop.onlineshop.model.view.AuthorViewModel;
import com.shop.onlineshop.model.view.CategoryViewModel;
import com.shop.onlineshop.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/all")
    public List<AuthorViewModel> getAllAuthors () {
        return authorService.getAllAuthors();
    }

    @GetMapping("/author/{id}")
    public AuthorViewModel getAuthorById (@PathVariable Long id) {

        return authorService.getAuthorById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<AuthorAddBindingModel> addAuthor (
            @Valid @RequestBody AuthorAddBindingModel authorAddBindingModel) {

        //TODO implement in service

        return ResponseEntity.ok().build();
    }
}
