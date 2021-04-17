package com.shop.onlineshop.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.OrderAddBindingModel;
import com.shop.onlineshop.model.binding.OrderUpdateBindingModel;
import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.entity.enums.StatusName;
import com.shop.onlineshop.model.view.OrdersViewModel;
import com.shop.onlineshop.service.OrderService;

import java.util.ArrayList;

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

@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(SpringExtension.class)
public class OrderControllerTest {
    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    @Test
    public void testDeleteOrder() throws Exception {
        doNothing().when(this.orderService).deleteById((Long) any());
        when(this.orderService.existById((Long) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/orders/delete/{id}", 1L);
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Order successfully deleted\"}")));
    }

    @Test
    public void testDeleteOrder2() throws Exception {
        doNothing().when(this.orderService).deleteById((Long) any());
        when(this.orderService.existById((Long) any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/orders/delete/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Order with id 1 does not exist\"}")));
    }

    @Test
    public void testGetAllOrders() throws Exception {
        when(this.orderService.getAllOrders()).thenReturn(new ArrayList<OrdersViewModel>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/all");
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testGetAllOrders2() throws Exception {
        when(this.orderService.getAllOrders()).thenReturn(new ArrayList<OrdersViewModel>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/orders/all");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testGetAllOrders3() throws Exception {
        when(this.orderService.getAllOrders()).thenReturn(new ArrayList<OrdersViewModel>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/all");
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testGetAllOrders4() throws Exception {
        when(this.orderService.getAllOrders()).thenReturn(new ArrayList<OrdersViewModel>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/orders/all");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testAddOrder() throws Exception {
        doNothing().when(this.orderService).addOrder((OrderAddBindingModel) any());

        OrderAddBindingModel orderAddBindingModel = new OrderAddBindingModel();
        orderAddBindingModel.setUsername("janedoe");
        orderAddBindingModel.setBooks(new ArrayList<BookEntity>());
        String content = (new ObjectMapper()).writeValueAsString(orderAddBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Orders successfully added\"}")));
    }

    @Test
    public void testAddOrder2() throws Exception {
        doNothing().when(this.orderService).addOrder((OrderAddBindingModel) any());

        OrderAddBindingModel orderAddBindingModel = new OrderAddBindingModel();
        orderAddBindingModel.setUsername("janedoe");
        orderAddBindingModel.setBooks(new ArrayList<BookEntity>());
        String content = (new ObjectMapper()).writeValueAsString(orderAddBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Orders successfully added\"}")));
    }

    @Test
    public void testGetOrdersByUsername2() throws Exception {
        when(this.orderService.getUserOrders(anyString())).thenReturn(new OrdersViewModel());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/user").param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"id\":0,\"user\":null,\"books\":null,\"status\":null}")));
    }

//    @Test
//    public void testUpdateOrder() throws Exception {
//        doNothing().when(this.orderService).updateOrder((OrderUpdateBindingModel) any());
//
//        OrderUpdateBindingModel orderUpdateBindingModel = new OrderUpdateBindingModel();
//        orderUpdateBindingModel.setStatusName(StatusName.NEW);
//        orderUpdateBindingModel.setId(123L);
//        String content = (new ObjectMapper()).writeValueAsString(orderUpdateBindingModel);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders/update")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content);
//        MockMvcBuilders.standaloneSetup(this.orderController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content()
//                        .string(Matchers.containsString("{\"message\":\"Orders successfully updated\"}")));
//    }

    @Test
    public void testUpdateOrder2() throws Exception {
        doNothing().when(this.orderService).updateOrder((OrderUpdateBindingModel) any());
        when(this.orderService.existById((Long) any())).thenReturn(true);

        OrderUpdateBindingModel orderUpdateBindingModel = new OrderUpdateBindingModel();
        orderUpdateBindingModel.setStatusName(StatusName.NEW);
        orderUpdateBindingModel.setId(123L);
        String content = (new ObjectMapper()).writeValueAsString(orderUpdateBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Orders successfully updated\"}")));
    }

    @Test
    public void testUpdateOrder3() throws Exception {
        doNothing().when(this.orderService).updateOrder((OrderUpdateBindingModel) any());
        when(this.orderService.existById((Long) any())).thenReturn(false);

        OrderUpdateBindingModel orderUpdateBindingModel = new OrderUpdateBindingModel();
        orderUpdateBindingModel.setStatusName(StatusName.NEW);
        orderUpdateBindingModel.setId(123L);
        String content = (new ObjectMapper()).writeValueAsString(orderUpdateBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Order with id 123 does not exist\"}")));
    }

    @Test
    public void testUpdateOrder4() throws Exception {
        doNothing().when(this.orderService).updateOrder((OrderUpdateBindingModel) any());
        when(this.orderService.existById((Long) any())).thenReturn(true);

        OrderUpdateBindingModel orderUpdateBindingModel = new OrderUpdateBindingModel();
        orderUpdateBindingModel.setStatusName(StatusName.NEW);
        orderUpdateBindingModel.setId(null);
        String content = (new ObjectMapper()).writeValueAsString(orderUpdateBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string(Matchers.containsString("{\"message\":\"Please provide an id\"}")));
    }

    @Test
    public void testGetOrdersByUsername() throws Exception {
        when(this.orderService.getUserOrders(anyString())).thenReturn(new OrdersViewModel());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/user").param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"id\":0,\"user\":null,\"books\":null,\"status\":null}")));
    }
}

