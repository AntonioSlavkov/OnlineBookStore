package com.shop.onlineshop.service;

import com.shop.onlineshop.exception.AuthorNotFoundException;
import com.shop.onlineshop.exception.BookNotFoundException;
import com.shop.onlineshop.exception.CategoryAlreadyExistException;
import com.shop.onlineshop.exception.CategoryNotFountException;
import com.shop.onlineshop.mapper.CategoryAddMapper;
import com.shop.onlineshop.mapper.CategoryViewMapper;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.view.AuthorViewModel;
import com.shop.onlineshop.model.view.BookViewModel;
import com.shop.onlineshop.model.view.CategoryViewModel;
import com.shop.onlineshop.repository.CategoryRepository;
import com.shop.onlineshop.service.impl.AuthorServiceImpl;
import com.shop.onlineshop.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryServiceImplTest;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryViewMapper categoryViewMapper;

    @Mock
    CategoryAddMapper categoryAddMapper;

    long TEST_CATEGORY1_ID = 1, TEST_CATEGORY2_ID = 2;
    String TEST_CATEGORY1_NAME = "Fantasy", TEST_CATEGORY2_NAME = "Drama";
    int NON_EXISTING_CATEGORY = 6666;
    CategoryEntity category1, category2;
    int NEW_CATEGORY_ID = 3;

    @BeforeEach
    public void setUP () {

        category1 = new CategoryEntity();
        category1.setCategory(TEST_CATEGORY1_NAME);
        category1.setId(TEST_CATEGORY1_ID);

        category2 = new CategoryEntity();
        category2.setCategory(TEST_CATEGORY2_NAME);
        category2.setId(TEST_CATEGORY2_ID);

        categoryServiceImplTest = new CategoryServiceImpl(this.categoryRepository,
                this.categoryViewMapper, this.categoryAddMapper);
    }

    @Test
    public void testFindByName () {

        when(categoryRepository.findByCategory(category2.getCategory())).thenReturn(Optional.of(category2));
        CategoryEntity category = new CategoryEntity();
        category.setCategory("Drama");

        CategoryEntity categoryTest = categoryServiceImplTest
                .findByName(category.getCategory());

        Assertions.assertEquals(category.getCategory(), categoryTest.getCategory());
    }
    @Test
    public void testFindByNameThrowsCategoryNotFound () {
        when(categoryRepository.findByCategory(any())).thenThrow(CategoryNotFountException.class);

        assertThrows(CategoryNotFountException.class, () -> {
            categoryServiceImplTest.findByName("Not Found");

        });
    }

    @Test
    public void testGetAllCategories () {

        List<CategoryEntity> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        given(categoryRepository.findAll()).willReturn(categories);

        List<CategoryViewModel> expected = categoryServiceImplTest.getAllCategories();

        assertEquals(expected, categoryViewMapper.categoryEntityToCategoryViewList(categories));
        verify(categoryRepository).findAll();
    }

    @Test
    public void testGetCategoryById () {
        when(categoryRepository.findById(category2.getId())).thenReturn(Optional.of(category2));
        CategoryEntity category = new CategoryEntity();
        category.setId(TEST_CATEGORY2_ID);

        CategoryViewModel expected = categoryServiceImplTest
                .getCategoryById(category.getId());

        assertThat(expected).isSameAs(categoryViewMapper.categoryEntityToCategoryViewModel(category));
        verify(categoryRepository).findById(category.getId());
    }

    @Test
    public void testGetCategoryByIdShouldThrowCategoryAlreadyExist () {


        when(categoryRepository.findById(any())).thenThrow(CategoryAlreadyExistException.class);

        assertThrows(CategoryAlreadyExistException.class, () -> {
            categoryServiceImplTest.getCategoryById(NON_EXISTING_CATEGORY);
        });
    }
}
