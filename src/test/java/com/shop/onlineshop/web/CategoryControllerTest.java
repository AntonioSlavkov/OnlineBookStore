package com.shop.onlineshop.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.CategoryAddBindingModel;
import com.shop.onlineshop.model.view.CategoryViewModel;
import com.shop.onlineshop.service.CategoryService;

import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CategoryController.class})
@ExtendWith(SpringExtension.class)
public class CategoryControllerTest {
    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testAddListOfCategories() throws Exception {
        doNothing().when(this.categoryService).addCategory((CategoryAddBindingModel) any());

        CategoryAddBindingModel categoryAddBindingModel = new CategoryAddBindingModel();
        categoryAddBindingModel.setCategory("Category");
        String content = (new ObjectMapper()).writeValueAsString(categoryAddBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/categories/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllCategories() throws Exception {
        when(this.categoryService.getAllCategories()).thenReturn(new ArrayList<CategoryViewModel>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories/all");
        MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testGetAllCategories2() throws Exception {
        when(this.categoryService.getAllCategories()).thenReturn(new ArrayList<CategoryViewModel>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/categories/all");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testGetCategoryById() throws Exception {
        when(this.categoryService.getCategoryById(anyLong())).thenReturn(new CategoryViewModel());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories/category/{id}",
                12345678987654321L);
        MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\"category\":null}")));
    }

    @Test
    public void testGetCategoryById2() throws Exception {
        when(this.categoryService.getCategoryById(anyLong())).thenReturn(new CategoryViewModel());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/categories/category/{id}",
                12345678987654321L);
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\"category\":null}")));
    }
}

