package com.shop.onlineshop.web;

import com.shop.onlineshop.model.binding.UserAddBindingModel;
import com.shop.onlineshop.model.message.MessageDto;
import com.shop.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowedHeaders = "*")
public class RegisterController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser (@Valid @RequestBody UserAddBindingModel userAddBindingModel) {

        userService.registerUser(userAddBindingModel);
        return ResponseEntity.ok().body(new MessageDto("User registered Successfully"));
    }
}
