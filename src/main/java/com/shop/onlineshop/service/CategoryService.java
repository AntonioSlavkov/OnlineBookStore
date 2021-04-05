package com.shop.onlineshop.service;

import com.shop.onlineshop.model.binding.CategoryAddBindingModel;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.view.CategoryViewModel;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
//    void initCategories();

    CategoryEntity findByName(String name);

    List<CategoryViewModel> getAllCategories();

    CategoryEntity saveCategory(CategoryEntity mainCategory);

    CategoryViewModel getCategoryById(long id);

    void addCategories(CategoryAddBindingModel categoryAddBindingModel);
}
