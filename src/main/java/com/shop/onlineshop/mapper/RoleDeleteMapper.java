package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.binding.UserAddRoleBindingModel;
import com.shop.onlineshop.model.binding.UserDeleteRoleBindingModel;
import com.shop.onlineshop.model.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleDeleteMapper {

    @Mapping(ignore = true, target = "id")
    RoleEntity userDeleteRoleBindingModelToRoleEntity(UserDeleteRoleBindingModel userDeleteRoleBindingModel);
}
