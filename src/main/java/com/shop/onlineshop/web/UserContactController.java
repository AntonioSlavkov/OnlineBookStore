package com.shop.onlineshop.web;

import com.shop.onlineshop.model.binding.UserContactAddBindingModel;
import com.shop.onlineshop.model.message.MessageDto;
import com.shop.onlineshop.model.view.UserContactViewModel;
import com.shop.onlineshop.service.UserContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
@AllArgsConstructor
@CrossOrigin("*")
public class UserContactController {
    //TODO implement get, add and update.
    private final UserContactService userContactService;

    @GetMapping("/contact")
    public UserContactViewModel getUserContact (@RequestParam String username) {

        return userContactService.getUserContacts(username);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUserContact (@RequestBody UserContactAddBindingModel userContactAddBindingModel) {

        userContactService.updateUserContacts(userContactAddBindingModel);
        return ResponseEntity.ok().body(new MessageDto("Successfully updated user contacts"));
    }
}
