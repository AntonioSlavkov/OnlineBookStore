package com.shop.onlineshop.model.view;

import com.shop.onlineshop.model.entity.enums.RoleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RoleViewModel {

    private Set<RoleName> roles;
}
