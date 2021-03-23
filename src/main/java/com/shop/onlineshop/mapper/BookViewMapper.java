package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.view.BookViewModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookViewMapper {

    BookViewModel bookEntityToBookViewModel (BookEntity bookEntity);

    List<BookViewModel> bookEntityToBookViewModelList(List<BookEntity> bookEntity);
}
