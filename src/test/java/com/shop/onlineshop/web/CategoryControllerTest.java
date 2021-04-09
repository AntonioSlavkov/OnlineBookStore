package com.shop.onlineshop.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.CategoryAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.repository.AuthorRepository;
import com.shop.onlineshop.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryRepository categoryRepository;

    long TEST_CATEGORY1_ID = 1, TEST_CATEGORY2_ID = 2;
    String TEST_CATEGORY1_NAME = "Fantasy", TEST_CATEGORY2_NAME = "Drama";
    int NON_EXISTING_CATEGORY = 6666;
    CategoryEntity category1, category2;
    int NEW_CATEGORY_ID = 3;

    @BeforeEach
    public void setUp () {

        category1 = new CategoryEntity();
        category1.setCategory(TEST_CATEGORY1_NAME);
        category1.setId(TEST_CATEGORY1_ID);

        category2 = new CategoryEntity();
        category2.setCategory(TEST_CATEGORY2_NAME);
        category2.setId(TEST_CATEGORY2_ID);

        when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));
        when(categoryRepository.findById(category2.getId())).thenReturn(Optional.of(category2));
        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        when(categoryRepository.save(any())).thenAnswer(
                (Answer<CategoryEntity>) invocation -> {
                    CategoryEntity categoryToSave = invocation.getArgument(0);
                    categoryToSave.setId(NEW_CATEGORY_ID);
                    return categoryToSave;
                }
        );
    }

    @AfterEach
    public void tearDown () {
        categoryRepository.deleteAll();
    }

    @Test
    public void testGetAllCategoriesShouldReturnCorrectStatus () throws Exception {
        this.mockMvc
                .perform(get("/categories/all")).andExpect(status().isOk());
    }

    @Test
    public void testCategoryNotFoundById () throws Exception {
        this.mockMvc
                .perform(get("/categories/category/{id}", NON_EXISTING_CATEGORY))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCategoryFound() throws Exception {
        this.mockMvc
                .perform(get("/categories/category/{id}", TEST_CATEGORY1_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category", is(category1.getCategory())));
    }

    @Test
    public void testCategoryAdd () throws Exception {

        CategoryAddBindingModel categoryToAdd = new CategoryAddBindingModel();
        categoryToAdd.setCategory("Thriller");

        String json = this.objectMapper.writeValueAsString(categoryToAdd);

        this.mockMvc
                .perform(post("/categories/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<CategoryEntity> argument = ArgumentCaptor.forClass(CategoryEntity.class);
        Mockito.verify(categoryRepository, times(1)).save(argument.capture());

        CategoryEntity newCategoryActual = argument.getValue();

        Assertions.assertEquals(categoryToAdd.getCategory(), newCategoryActual.getCategory());
        Assertions.assertEquals(NEW_CATEGORY_ID, newCategoryActual.getId());
    }

}
