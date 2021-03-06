package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.binding.BookAddBindingModel;
import com.shop.onlineshop.model.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookAddMapper {


    @Mapping(ignore = true, target = "id")
    BookEntity BookAddBindingToBookEntity(BookAddBindingModel bookAddBindingModel);
}
