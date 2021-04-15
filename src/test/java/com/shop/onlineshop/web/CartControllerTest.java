package com.shop.onlineshop.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.AddBookToUserCart;
import com.shop.onlineshop.model.view.CartViewModel;
import com.shop.onlineshop.service.CartService;

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

@ContextConfiguration(classes = {CartController.class})
@ExtendWith(SpringExtension.class)
public class CartControllerTest {
    @Autowired
    private CartController cartController;

    @MockBean
    private CartService cartService;

    @Test
    public void testGetUserCart() throws Exception {
        when(this.cartService.getUserCart(anyString())).thenReturn(new ArrayList<CartViewModel>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cart/all").param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testAddBookToUserCart() throws Exception {
        doNothing().when(this.cartService).addBookToUserCart((AddBookToUserCart) any());

        AddBookToUserCart addBookToUserCart = new AddBookToUserCart();
        addBookToUserCart.setUsername("janedoe");
        addBookToUserCart.setId(123L);
        String content = (new ObjectMapper()).writeValueAsString(addBookToUserCart);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Book successfully added to cart\"}")));
    }
}

