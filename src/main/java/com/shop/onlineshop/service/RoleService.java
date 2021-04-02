package com.shop.onlineshop.service;

import com.shop.onlineshop.model.binding.RoleAddBindingModel;
import com.shop.onlineshop.model.binding.UserAddRoleBindingModel;
import com.shop.onlineshop.model.binding.UserDeleteRoleBindingModel;
import com.shop.onlineshop.model.entity.enums.RoleName;
import com.shop.onlineshop.model.view.RoleViewModel;

import java.util.List;

public interface RoleService {
    List<RoleViewModel> getUserRoles(String username);

    void initRoles();

    void addRoleToUser(UserAddRoleBindingModel userAddRoleBindingModel);

    void deleteRoleToUser(String username, RoleName roleName);
}
