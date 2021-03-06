package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.view.AuthorViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorViewMapper {

    List<AuthorViewModel> authorEntityToAuthorViewList (List<AuthorEntity> authorEntities);

    AuthorViewModel authorEntityToAuthorView (AuthorEntity authorEntity);

    @Mapping(ignore = true ,target = "id")
    List<AuthorEntity> authorViewToAuthorEntity (List<AuthorViewModel> authorViewModel);

}
