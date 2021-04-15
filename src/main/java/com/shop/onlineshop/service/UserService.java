package com.shop.onlineshop.service;

import com.shop.onlineshop.model.binding.UserAddBindingModel;
import com.shop.onlineshop.model.entity.UserEntity;

public interface UserService {


    void registerUser(UserAddBindingModel userAddBindingModel);

    UserEntity findUserByUsername (String username);

    void seedUser();

    boolean existsByUsername (String username);

    boolean existsByEmail(String email);
}
