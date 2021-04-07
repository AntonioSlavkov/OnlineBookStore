package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.binding.UserContactAddBindingModel;
import com.shop.onlineshop.model.entity.UserContactEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserContactAddMapper {

    @Mapping(ignore = true, target = "id")
    UserContactEntity userContactAddBindingToUserContactEntity
            (UserContactAddBindingModel userContactAddBindingModel);
}
