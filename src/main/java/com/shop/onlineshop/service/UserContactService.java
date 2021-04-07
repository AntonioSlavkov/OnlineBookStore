package com.shop.onlineshop.service;

import com.shop.onlineshop.model.binding.UserContactAddBindingModel;
import com.shop.onlineshop.model.view.UserContactViewModel;

public interface UserContactService {
    UserContactViewModel getUserContacts(String username);

    void updateUserContacts(UserContactAddBindingModel userContactAddBindingModel);
}
