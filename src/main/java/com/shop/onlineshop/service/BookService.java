package com.shop.onlineshop.service;

import com.shop.onlineshop.model.binding.BookAddBindingModel;

import com.shop.onlineshop.model.view.BookViewModel;


import java.util.List;

public interface BookService {
//    void initBooks();

    List<BookViewModel> getAllBooks ();

    BookViewModel getBookById (long id);

    void addBook (BookAddBindingModel bookAddBindingModel);


    void deleteBookById(Long id);

    boolean existsByBookTitle (String title);

//    void updateBook(Long id, BookUpdateBindingModel bookUpdateBindingModel);
}
