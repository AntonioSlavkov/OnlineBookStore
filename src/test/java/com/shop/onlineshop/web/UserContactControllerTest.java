package com.shop.onlineshop.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.UserContactAddBindingModel;
import com.shop.onlineshop.model.view.UserContactViewModel;
import com.shop.onlineshop.service.UserContactService;
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

@ContextConfiguration(classes = {UserContactController.class})
@ExtendWith(SpringExtension.class)
public class UserContactControllerTest {
    @Autowired
    private UserContactController userContactController;

    @MockBean
    private UserContactService userContactService;

    @Test
    public void testGetUserContact() throws Exception {
        when(this.userContactService.getUserContacts(anyString())).thenReturn(new UserContactViewModel());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/contacts/contact")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.userContactController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"phoneNumber\":null,\"city\":null,\"address\":null}")));
    }

    @Test
    public void testUpdateUserContact() throws Exception {
        doNothing().when(this.userContactService).updateUserContacts((UserContactAddBindingModel) any());

        UserContactAddBindingModel userContactAddBindingModel = new UserContactAddBindingModel();
        userContactAddBindingModel.setUsername("janedoe");
        userContactAddBindingModel.setCity("Oxford");
        userContactAddBindingModel.setPhoneNumber("4105551212");
        userContactAddBindingModel.setAddress("42 Main St");
        String content = (new ObjectMapper()).writeValueAsString(userContactAddBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/contacts/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userContactController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Successfully updated user contacts\"}")));
    }
}

