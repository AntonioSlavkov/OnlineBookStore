package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.mapper.CartViewMapper;
import com.shop.onlineshop.model.binding.AddBookToUserCart;
import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.entity.CartEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.view.BookViewModel;
import com.shop.onlineshop.model.view.CartViewModel;
import com.shop.onlineshop.repository.CartRepository;
import com.shop.onlineshop.service.BookService;
import com.shop.onlineshop.service.CartService;
import com.shop.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final CartRepository cartRepository;
    private final BookService bookService;
    private final CartViewMapper cartViewMapper;


    @Override
    public List<CartViewModel> getUserCart(String username) {

        if (!userService.existsByUsername(username)) {
            throw new UsernameNotFoundException("User with " + username + "does not exist.");
        }

        return cartViewMapper.cartEntityToCartViewModelList(cartRepository.findAll());
    }

    @Override
    public void addBookToUserCart(AddBookToUserCart addBookToUserCart) {

        if (!userService.existsByUsername(addBookToUserCart.getUsername())) {
            throw new UsernameNotFoundException("User with " + addBookToUserCart.getUsername() + "does not exist.");
        }

        UserEntity user = userService.findUserByUsername(addBookToUserCart.getUsername());
        BookViewModel book = bookService.getBookById(addBookToUserCart.getId());

        CartEntity cart =  new CartEntity();
        cart.setBookId(book.getId());
        cart.setUserId(user.getId());
        cartRepository.save(cart);

    }

    @Override
    public void deleteUserCart(long id) {
        cartRepository.deleteByUserId(id);
    }
}
