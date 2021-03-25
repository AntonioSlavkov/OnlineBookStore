package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.mapper.UserAddMapper;
import com.shop.onlineshop.model.binding.UserAddBindingModel;
import com.shop.onlineshop.model.entity.RoleEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.entity.enums.RoleName;
import com.shop.onlineshop.repository.RoleRepository;
import com.shop.onlineshop.repository.UserRepository;
import com.shop.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserAddMapper userAddMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserAddBindingModel userAddBindingModel) {

        UserEntity newUser = userAddMapper.userAddBindingToUserEntity(userAddBindingModel);
        newUser.setPassword(passwordEncoder.encode(userAddBindingModel.getPassword()));

        RoleEntity userRoles = roleRepository
                .findByRole(RoleName.REGULAR)
                .orElseThrow(() -> new IllegalStateException("REGULAR role not found. Please seed the roles."));

        newUser.addRole(userRoles);
        userRepository.save(newUser);
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("Username with name " + username + " does not exist"));
    }
}
