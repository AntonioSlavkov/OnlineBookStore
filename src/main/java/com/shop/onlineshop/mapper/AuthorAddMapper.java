package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.binding.AuthorAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorAddMapper {


    @Mapping(target = "author", source = "authors")
    @Mapping(ignore = true, target = "id")
    AuthorEntity authorAddBindingToAuthorEntity (String authors);
}
