package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.model.entity.RoleEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.entity.enums.RoleName;
import com.shop.onlineshop.model.view.RoleViewModel;
import com.shop.onlineshop.repository.RoleRepository;
import com.shop.onlineshop.service.RoleService;
import com.shop.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserService userService;

    @Override
    public void getUserRoles(String username, RoleViewModel roleViewModel) {

        UserEntity user = userService.findUserByUsername(username);
        //TODO finish implementing getRoles


    }

    @Override
    public void initRoles() {
        if (roleRepository.count() == 0) {
            Arrays.stream(RoleName.values()).forEach(roleName -> {
                new RoleEntity();
                RoleEntity role;
                switch (roleName.toString()) {
                    case "REGULAR", "ADMIN", "ROOT_ADMIN" -> {
                        role = new RoleEntity(roleName);
                        roleRepository.save(role);
                    }
                }

            });
        }
    }
}
