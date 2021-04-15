package com.shop.onlineshop.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.BookAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.entity.PictureEntity;
import com.shop.onlineshop.model.view.BookViewModel;
import com.shop.onlineshop.service.BookService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
public class BookControllerTest {
    @Autowired
    private BookController bookController;

    @MockBean
    private BookService bookService;

    @Test
    public void testGetBookById() throws Exception {
        when(this.bookService.getBookById(anyLong())).thenReturn(new BookViewModel());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/book/{id}", 12345678987654321L);
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString(
                                "{\"id\":null,\"title\":null,\"pages\":0,\"language\":null,\"description\":null,\"price\":null,\"pictureUrls\":null"
                                        + ",\"author\":null,\"mainCategory\":null,\"subCategories\":null}")));
    }

    @Test
    public void testAddBook() throws Exception {
        when(this.bookService.existsByBookTitle(anyString())).thenReturn(true);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(123L);
        categoryEntity.setCategory("Category");

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(123L);
        authorEntity.setAuthor("JaneDoe");

        BookAddBindingModel bookAddBindingModel = new BookAddBindingModel();
        bookAddBindingModel.setLanguage("Language");
        bookAddBindingModel.setPictureUrls(new ArrayList<PictureEntity>());
        bookAddBindingModel.setMainCategory(categoryEntity);
        bookAddBindingModel.setSubCategories(new HashSet<CategoryEntity>());
        bookAddBindingModel.setPages(1);
        bookAddBindingModel.setPrice(BigDecimal.valueOf(42L));
        bookAddBindingModel.setTitle("Dr");
        bookAddBindingModel.setDescription("The characteristics of someone or something");
        bookAddBindingModel.setAuthor(authorEntity);
        String content = (new ObjectMapper()).writeValueAsString(bookAddBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Book with title Dr already exists.\"}")));
    }

    @Test
    public void testAddBook2() throws Exception {
        doNothing().when(this.bookService).addBook((BookAddBindingModel) any());
        when(this.bookService.existsByBookTitle(anyString())).thenReturn(false);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(123L);
        categoryEntity.setCategory("Category");

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(123L);
        authorEntity.setAuthor("JaneDoe");

        BookAddBindingModel bookAddBindingModel = new BookAddBindingModel();
        bookAddBindingModel.setLanguage("Language");
        bookAddBindingModel.setPictureUrls(new ArrayList<PictureEntity>());
        bookAddBindingModel.setMainCategory(categoryEntity);
        bookAddBindingModel.setSubCategories(new HashSet<CategoryEntity>());
        bookAddBindingModel.setPages(1);
        bookAddBindingModel.setPrice(BigDecimal.valueOf(42L));
        bookAddBindingModel.setTitle("Dr");
        bookAddBindingModel.setDescription("The characteristics of someone or something");
        bookAddBindingModel.setAuthor(authorEntity);
        String content = (new ObjectMapper()).writeValueAsString(bookAddBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Book added successfully.\"}")));
    }

    @Test
    public void testDeleteBook() throws Exception {
        doNothing().when(this.bookService).deleteBookById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/books/delete/{id}", 1L);
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Book deleted successfully.\"}")));
    }

    @Test
    public void testDeleteBook2() throws Exception {
        doNothing().when(this.bookService).deleteBookById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/books/delete/{id}", 1L);
        deleteResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Book deleted successfully.\"}")));
    }

    @Test
    public void testGetAllBook() throws Exception {
        when(this.bookService.getAllBooks()).thenReturn(new ArrayList<BookViewModel>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/all");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testGetAllBook2() throws Exception {
        when(this.bookService.getAllBooks()).thenReturn(new ArrayList<BookViewModel>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/books/all");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }
}

