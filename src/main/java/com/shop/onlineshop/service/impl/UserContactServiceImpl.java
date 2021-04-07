package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.mapper.UserContactAddMapper;
import com.shop.onlineshop.mapper.UserContactViewMapper;
import com.shop.onlineshop.model.binding.UserContactAddBindingModel;
import com.shop.onlineshop.model.entity.UserContactEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.view.UserContactViewModel;
import com.shop.onlineshop.repository.UserContactRepository;
import com.shop.onlineshop.repository.UserRepository;
import com.shop.onlineshop.service.UserContactService;
import com.shop.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserContactServiceImpl implements UserContactService {

    private final UserContactRepository userContactRepository;
    private final UserService userService;
    private final UserContactViewMapper userContactViewMapper;
    private final UserContactAddMapper userContactAddMapper;
    private final UserRepository userRepository;

    @Override
    public UserContactViewModel getUserContacts(String username) {

        UserEntity user = userService.findUserByUsername(username);

        return userContactViewMapper.userContactEntityToUserContactViewModel(user.getUserContactEntity());
    }

    @Override
    public void updateUserContacts(UserContactAddBindingModel userContactAddBindingModel) {

        UserEntity user = userService
                .findUserByUsername(userContactAddBindingModel.getUsername());

        UserContactEntity userContactEntity = userContactAddMapper
                .userContactAddBindingToUserContactEntity(userContactAddBindingModel);

        user.setUserContactEntity(userContactEntity);
        userRepository.save(user);
    }
}
