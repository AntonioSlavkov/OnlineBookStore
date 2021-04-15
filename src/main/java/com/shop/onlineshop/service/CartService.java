package com.shop.onlineshop.service;

import com.shop.onlineshop.model.binding.AddBookToUserCart;
import com.shop.onlineshop.model.view.CartViewModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {

    List<CartViewModel> getUserCart(String username);

    void addBookToUserCart(AddBookToUserCart addBookToUserCart);

    void deleteUserCart(long id);
}
