package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.binding.BookUpdateBindingModel;
import com.shop.onlineshop.model.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookUpdateMapper {

    @Mapping(ignore = true, target = "id")
    BookEntity bookUpdateBindingToBookEntity (BookUpdateBindingModel bookUpdateBindingModel);
}
