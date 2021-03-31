package com.shop.onlineshop.service;

import com.shop.onlineshop.model.binding.RoleAddBindingModel;
import com.shop.onlineshop.model.binding.UserAddRoleBindingModel;
import com.shop.onlineshop.model.binding.UserDeleteRoleBindingModel;
import com.shop.onlineshop.model.view.RoleViewModel;

import java.util.List;

public interface RoleService {
    List<RoleViewModel> getUserRoles(UserAddRoleBindingModel userAddRoleBindingModel);

    void initRoles();

    void addRoleToUser(UserAddRoleBindingModel userAddRoleBindingModel);

    void deleteRoleToUser(UserDeleteRoleBindingModel userDeleteRoleBindingModel);
}
