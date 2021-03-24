package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.view.CategoryViewModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryViewMapper {

    List<CategoryViewModel> categoryEntityToCategoryViewList (List<CategoryEntity> categoryEntities);

    CategoryViewModel categoryEntityToCategoryViewModel (CategoryEntity categoryEntity);
}
