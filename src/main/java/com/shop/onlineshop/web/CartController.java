package com.shop.onlineshop.web;

import com.shop.onlineshop.model.binding.AddBookToUserCart;
import com.shop.onlineshop.model.message.MessageDto;
import com.shop.onlineshop.model.view.CartViewModel;
import com.shop.onlineshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/all")
    public List<CartViewModel> getUserCart (@RequestParam String username) {

        return cartService.getUserCart(username);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addBookToUserCart(@RequestBody AddBookToUserCart addBookToUserCart) {

        cartService.addBookToUserCart(addBookToUserCart);
        return ResponseEntity.ok().body(new MessageDto("Book successfully added to cart"));
    }
}
