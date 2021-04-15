package com.shop.onlineshop.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.UserLoginBindingModel;
import com.shop.onlineshop.security.jwt.JwtUtils;
import com.shop.onlineshop.security.service.UserDetailsImpl;

import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {LoginController.class})
@ExtendWith(SpringExtension.class)
public class LoginControllerTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private LoginController loginController;

    @Test
    public void testLoginUser() throws Exception {
        when(this.jwtUtils.generateJwtToken((org.springframework.security.core.Authentication) any())).thenReturn("foo");
        when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any())).thenReturn(
                new TestingAuthenticationToken(new UserDetailsImpl("janedoe", "iloveyou", new ArrayList<GrantedAuthority>()),
                        "Credentials"));

        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
        userLoginBindingModel.setPassword("iloveyou");
        userLoginBindingModel.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userLoginBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers
                                .containsString("{\"token\":\"foo\",\"type\":\"Bearer\",\"username\":\"janedoe\",\"roles\":[]}")));
    }

    @Test
    public void testLogout() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/users/logout");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("userLoginBindingModel",
                String.valueOf(new UserLoginBindingModel()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.loginController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testLogout2() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/users/logout");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("userLoginBindingModel", String.valueOf(""));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.loginController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

