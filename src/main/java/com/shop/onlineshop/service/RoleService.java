package com.shop.onlineshop.service;

import com.shop.onlineshop.model.view.RoleViewModel;

public interface RoleService {
    void getUserRoles(String username, RoleViewModel roleViewModel);

    void initRoles();
}
