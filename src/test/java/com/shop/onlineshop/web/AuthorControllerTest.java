package com.shop.onlineshop.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.AuthorAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.view.AuthorViewModel;
import com.shop.onlineshop.service.AuthorService;

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

@ContextConfiguration(classes = {AuthorController.class})
@ExtendWith(SpringExtension.class)
public class AuthorControllerTest {
    @Autowired
    private AuthorController authorController;

    @MockBean
    private AuthorService authorService;

    @Test
    public void testGetAllAuthors() throws Exception {
        when(this.authorService.getAllAuthors()).thenReturn(new ArrayList<AuthorViewModel>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/authors/all");
        MockMvcBuilders.standaloneSetup(this.authorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testGetAllAuthors2() throws Exception {
        when(this.authorService.getAllAuthors()).thenReturn(new ArrayList<AuthorViewModel>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/authors/all");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.authorController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testGetAuthorById() throws Exception {
        when(this.authorService.getAuthorById((Long) any())).thenReturn(new AuthorViewModel());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/authors/author/{id}", 1L);
        MockMvcBuilders.standaloneSetup(this.authorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\"author\":null}")));
    }

    @Test
    public void testGetAuthorById2() throws Exception {
        when(this.authorService.getAuthorById((Long) any())).thenReturn(new AuthorViewModel());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/authors/author/{id}", 1L);
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.authorController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("{\"author\":null}")));
    }

    @Test
    public void testAddAuthor() throws Exception {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(123L);
        authorEntity.setAuthor("JaneDoe");
        when(this.authorService.addAuthor((AuthorAddBindingModel) any())).thenReturn(authorEntity);

        AuthorAddBindingModel authorAddBindingModel = new AuthorAddBindingModel();
        authorAddBindingModel.setAuthor("JaneDoe");
        String content = (new ObjectMapper()).writeValueAsString(authorAddBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.authorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Author successfully added\"}")));
    }
}

