package com.shop.onlineshop.model.binding;

import com.shop.onlineshop.model.entity.enums.CategoryName;
import com.shop.onlineshop.model.entity.enums.RoleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleAddBindingModel {

    private RoleName role;
}
