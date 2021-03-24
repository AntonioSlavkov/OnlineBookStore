package com.shop.onlineshop.service;

import com.shop.onlineshop.model.binding.CategoryAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.view.CategoryViewModel;

import java.util.List;

public interface CategoryService {
    void initCategories();

    CategoryEntity findByName(String name);

    List<CategoryViewModel> getAllCategories();

    CategoryEntity saveCategory(CategoryEntity mainCategory);

    CategoryViewModel getCategoryById(long id);

    void addCategory(CategoryAddBindingModel categoryAddBindingModel);
}
