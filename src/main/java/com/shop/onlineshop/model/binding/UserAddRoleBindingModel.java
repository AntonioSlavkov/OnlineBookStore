package com.shop.onlineshop.model.binding;

import com.shop.onlineshop.model.entity.enums.RoleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserAddRoleBindingModel {

    private String username;
    private RoleName roleName;
}
