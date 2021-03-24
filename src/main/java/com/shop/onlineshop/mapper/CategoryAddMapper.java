package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.binding.CategoryAddBindingModel;
import com.shop.onlineshop.model.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryAddMapper {

    @Mapping(ignore = true, target = "id")
    CategoryEntity categoryAddBindingToCategoryEntity (CategoryAddBindingModel categoryAddBindingModel);
}
