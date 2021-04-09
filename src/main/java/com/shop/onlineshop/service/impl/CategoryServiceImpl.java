package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.exception.CategoryAlreadyExistException;
import com.shop.onlineshop.exception.CategoryNotFountException;
import com.shop.onlineshop.mapper.CategoryAddMapper;
import com.shop.onlineshop.mapper.CategoryViewMapper;
import com.shop.onlineshop.model.binding.CategoryAddBindingModel;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.view.CategoryViewModel;
import com.shop.onlineshop.repository.CategoryRepository;
import com.shop.onlineshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryViewMapper categoryViewMapper;
    private final CategoryAddMapper categoryAddMapper;


    @Override
    public CategoryEntity findByName(String name) {
        return categoryRepository
                .findByCategory(name)
                .orElseThrow(() -> new CategoryNotFountException("Category does not exist", NOT_FOUND));
    }

    @Override
    public List<CategoryViewModel> getAllCategories() {
        return categoryViewMapper
                .categoryEntityToCategoryViewList(categoryRepository.findAll());
    }

    @Override
    public CategoryEntity saveCategory(CategoryEntity mainCategory) {
        return categoryRepository.save(mainCategory);
    }

    @Override
    public CategoryViewModel getCategoryById(long id) {

        CategoryEntity category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new CategoryNotFountException("This category does not exist", NOT_FOUND));

        return categoryViewMapper.categoryEntityToCategoryViewModel(category);
    }

    @Override
    public void addCategory(CategoryAddBindingModel categoryAddBindingModel) {

        if (existByCategory(categoryAddBindingModel.getCategory())) {
            throw new CategoryAlreadyExistException("This category already exists", CONFLICT);
        }

        CategoryEntity category = categoryAddMapper
                .categoryAddBindingToCategoryEntity(categoryAddBindingModel
                        .getCategory());
        categoryRepository.save(category);
    }

    @Override
    public boolean existByCategory(String name) {
        return categoryRepository.existsCategoryEntityByCategory(name);
    }
}
