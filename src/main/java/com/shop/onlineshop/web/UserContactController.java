package com.shop.onlineshop.web;

import com.shop.onlineshop.model.view.UserContactViewModel;
import com.shop.onlineshop.service.UserContactService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
@AllArgsConstructor
@CrossOrigin("*")
public class UserContactController {
    //TODO implement get, add and update.
    private final UserContactService userContactService;

    @GetMapping("/contact")
    public UserContactViewModel getUserContact () {

        return null;
    }
}
