package com.shop.onlineshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.onlineshop.exception.CategoryAlreadyExistException;
import com.shop.onlineshop.exception.CategoryNotFountException;
import com.shop.onlineshop.mapper.CategoryAddMapper;
import com.shop.onlineshop.mapper.CategoryViewMapper;
import com.shop.onlineshop.model.binding.CategoryAddBindingModel;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.view.CategoryViewModel;
import com.shop.onlineshop.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CategoryServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class CategoryServiceImplTest {
    @MockBean
    private CategoryAddMapper categoryAddMapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @MockBean
    private CategoryViewMapper categoryViewMapper;

    @Test
    public void testFindByName() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(123L);
        categoryEntity.setCategory("Category");
        Optional<CategoryEntity> ofResult = Optional.<CategoryEntity>of(categoryEntity);
        when(this.categoryRepository.findByCategory(anyString())).thenReturn(ofResult);
        assertSame(categoryEntity, this.categoryServiceImpl.findByName("Name"));
        verify(this.categoryRepository).findByCategory(anyString());
    }

    @Test
    public void testFindByName2() {
        when(this.categoryRepository.findByCategory(anyString())).thenReturn(Optional.<CategoryEntity>empty());
        assertThrows(CategoryNotFountException.class, () -> this.categoryServiceImpl.findByName("Name"));
        verify(this.categoryRepository).findByCategory(anyString());
    }

    @Test
    public void testGetAllCategories() {
        ArrayList<CategoryViewModel> categoryViewModelList = new ArrayList<CategoryViewModel>();
        when(this.categoryViewMapper.categoryEntityToCategoryViewList((List<CategoryEntity>) any()))
                .thenReturn(categoryViewModelList);
        when(this.categoryRepository.findAll()).thenReturn(new ArrayList<CategoryEntity>());
        List<CategoryViewModel> actualAllCategories = this.categoryServiceImpl.getAllCategories();
        assertSame(categoryViewModelList, actualAllCategories);
        assertTrue(actualAllCategories.isEmpty());
        verify(this.categoryRepository).findAll();
        verify(this.categoryViewMapper).categoryEntityToCategoryViewList((List<CategoryEntity>) any());
    }

    @Test
    public void testSaveCategory() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(123L);
        categoryEntity.setCategory("Category");
        when(this.categoryRepository.save((CategoryEntity) any())).thenReturn(categoryEntity);
        assertSame(categoryEntity, this.categoryServiceImpl.saveCategory(new CategoryEntity("Category")));
        verify(this.categoryRepository).save((CategoryEntity) any());
    }

    @Test
    public void testGetCategoryById() {
        CategoryViewModel categoryViewModel = new CategoryViewModel();
        when(this.categoryViewMapper.categoryEntityToCategoryViewModel((CategoryEntity) any()))
                .thenReturn(categoryViewModel);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(123L);
        categoryEntity.setCategory("Category");
        Optional<CategoryEntity> ofResult = Optional.<CategoryEntity>of(categoryEntity);
        when(this.categoryRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(categoryViewModel, this.categoryServiceImpl.getCategoryById(123L));
        verify(this.categoryRepository).findById((Long) any());
        verify(this.categoryViewMapper).categoryEntityToCategoryViewModel((CategoryEntity) any());
    }

    @Test
    public void testGetCategoryById2() {
        when(this.categoryViewMapper.categoryEntityToCategoryViewModel((CategoryEntity) any()))
                .thenReturn(new CategoryViewModel());
        when(this.categoryRepository.findById((Long) any())).thenReturn(Optional.<CategoryEntity>empty());
        assertThrows(CategoryNotFountException.class, () -> this.categoryServiceImpl.getCategoryById(123L));
        verify(this.categoryRepository).findById((Long) any());
    }

    @Test
    public void testAddCategory() {
        when(this.categoryRepository.existsCategoryEntityByCategory(anyString())).thenReturn(true);
        assertThrows(CategoryAlreadyExistException.class,
                () -> this.categoryServiceImpl.addCategory(new CategoryAddBindingModel()));
        verify(this.categoryRepository).existsCategoryEntityByCategory(anyString());
    }

    @Test
    public void testAddCategory2() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(123L);
        categoryEntity.setCategory("Category");
        when(this.categoryRepository.save((CategoryEntity) any())).thenReturn(categoryEntity);
        when(this.categoryRepository.existsCategoryEntityByCategory(anyString())).thenReturn(false);

        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setId(123L);
        categoryEntity1.setCategory("Category");
        when(this.categoryAddMapper.categoryAddBindingToCategoryEntity(anyString())).thenReturn(categoryEntity1);
        this.categoryServiceImpl.addCategory(new CategoryAddBindingModel());
        verify(this.categoryAddMapper).categoryAddBindingToCategoryEntity(anyString());
        verify(this.categoryRepository).save((CategoryEntity) any());
        verify(this.categoryRepository).existsCategoryEntityByCategory(anyString());
    }

    @Test
    public void testExistByCategory() {
        when(this.categoryRepository.existsCategoryEntityByCategory(anyString())).thenReturn(true);
        assertTrue(this.categoryServiceImpl.existByCategory("Name"));
        verify(this.categoryRepository).existsCategoryEntityByCategory(anyString());
    }
}

