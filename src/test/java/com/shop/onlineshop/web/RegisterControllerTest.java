package com.shop.onlineshop.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.UserAddBindingModel;
import com.shop.onlineshop.service.UserService;
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

@ContextConfiguration(classes = {RegisterController.class})
@ExtendWith(SpringExtension.class)
public class RegisterControllerTest {
    @Autowired
    private RegisterController registerController;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterUser() throws Exception {
        UserAddBindingModel userAddBindingModel = new UserAddBindingModel();
        userAddBindingModel.setLastName("Doe");
        userAddBindingModel.setEmail("jane.doe@example.org");
        userAddBindingModel.setPassword("iloveyou");
        userAddBindingModel.setUsername("janedoe");
        userAddBindingModel.setFirstName("Jane");
        String content = (new ObjectMapper()).writeValueAsString(userAddBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.registerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

