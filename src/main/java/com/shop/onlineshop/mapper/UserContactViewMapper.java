package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.entity.UserContactEntity;
import com.shop.onlineshop.model.view.UserContactViewModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserContactViewMapper {

    UserContactViewModel userContactEntityToUserContactViewModel (UserContactEntity userContactEntity);
}
