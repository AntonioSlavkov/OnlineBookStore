package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.binding.OrderAddBindingModel;
import com.shop.onlineshop.model.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderAddMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "status")
    OrderEntity orderAddBindingToOrderEntity (OrderAddBindingModel orderAddBindingModel);
}
