package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.binding.UserAddBindingModel;
import com.shop.onlineshop.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAddMapper {

    @Mapping(ignore = true, target = "roles")
    @Mapping(ignore = true, target = "id")
    UserEntity userAddBindingToUserEntity (UserAddBindingModel userAddBindingModel);
}
