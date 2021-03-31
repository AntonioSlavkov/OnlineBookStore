package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.exception.InvalidRoleException;
import com.shop.onlineshop.exception.RoleAlreadyExistException;
import com.shop.onlineshop.mapper.RoleViewMapper;
import com.shop.onlineshop.model.binding.UserAddRoleBindingModel;
import com.shop.onlineshop.model.binding.UserDeleteRoleBindingModel;
import com.shop.onlineshop.model.entity.RoleEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.entity.enums.RoleName;
import com.shop.onlineshop.model.view.RoleViewModel;
import com.shop.onlineshop.repository.RoleRepository;
import com.shop.onlineshop.repository.UserRepository;
import com.shop.onlineshop.service.RoleService;
import com.shop.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final RoleViewMapper roleViewMapper;
    private final UserRepository userRepository;

    @Override
    public List<RoleViewModel> getUserRoles(UserAddRoleBindingModel userAddRoleBindingModel) {

        UserEntity user = userService.findUserByUsername(userAddRoleBindingModel.getUsername());
        return user
                .getRoles()
                .stream()
                .map(roleViewMapper::roleEntityToRoleViewMapper)
                .collect(Collectors.toList());
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

    @Override
    public void addRoleToUser(UserAddRoleBindingModel userAddRoleBindingModel) {

        RoleName roleName = userAddRoleBindingModel.getRole();
        RoleEntity role = roleRepository.findByRole(roleName).orElseThrow( () -> new InvalidRoleException(
                "Role is invalid", HttpStatus.BAD_REQUEST));

        UserEntity user = userService
                .findUserByUsername(userAddRoleBindingModel.getUsername());

        if (!user.getUsername().equals(userAddRoleBindingModel.getUsername())) {
            throw new UsernameNotFoundException(
                    "User with username " + userAddRoleBindingModel.getUsername() + " does not exist");
        }

        if (user.getRoles().contains(role)) {
            throw new RoleAlreadyExistException(
                    "This user already has this role", HttpStatus.CONFLICT);
        }

        user.addRole(role);
        userRepository.save(user);

    }

    @Override
    public void deleteRoleToUser(UserDeleteRoleBindingModel userDeleteRoleBindingModel) {

        RoleName roleName = userDeleteRoleBindingModel.getRole();
        RoleEntity role = roleRepository.findByRole(roleName).orElseThrow( () -> new InvalidRoleException(
                "Role is invalid", HttpStatus.BAD_REQUEST));

        UserEntity user = userService.findUserByUsername(userDeleteRoleBindingModel.getUsername());

        if (!user.getUsername().equals(userDeleteRoleBindingModel.getUsername())) {
            throw new UsernameNotFoundException(
                    "User with username " + userDeleteRoleBindingModel.getUsername() + " does not exist");
        }

        user.deleteRole(role);
        userRepository.save(user);
    }
}
