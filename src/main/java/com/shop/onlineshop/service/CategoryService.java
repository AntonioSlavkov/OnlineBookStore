package com.shop.onlineshop.service;

import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.CategoryEntity;

public interface CategoryService {
    void initCategories();

    CategoryEntity findByName(String name);
}
