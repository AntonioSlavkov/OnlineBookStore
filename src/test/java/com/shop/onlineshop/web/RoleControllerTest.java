package com.shop.onlineshop.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.UserAddRoleBindingModel;
import com.shop.onlineshop.model.entity.enums.RoleName;
import com.shop.onlineshop.model.view.RoleViewModel;
import com.shop.onlineshop.service.RoleService;
import com.shop.onlineshop.service.UserService;

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

@ContextConfiguration(classes = {RoleController.class})
@ExtendWith(SpringExtension.class)
public class RoleControllerTest {
    @Autowired
    private RoleController roleController;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserService userService;

    @Test
    public void testGetUserRoles() throws Exception {
        when(this.roleService.getUserRoles(anyString())).thenReturn(new ArrayList<RoleViewModel>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/roles/all").param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.roleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testAddRoleToUser() throws Exception {
        when(this.userService.existsByUsername(anyString())).thenReturn(true);
        doNothing().when(this.roleService).addRoleToUser((UserAddRoleBindingModel) any());

        UserAddRoleBindingModel userAddRoleBindingModel = new UserAddRoleBindingModel();
        userAddRoleBindingModel.setUsername("janedoe");
        userAddRoleBindingModel.setRoleName(RoleName.ROOT_ADMIN);
        String content = (new ObjectMapper()).writeValueAsString(userAddRoleBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/roles/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.roleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Role Added successfully\"}")));
    }

    @Test
    public void testAddRoleToUser2() throws Exception {
        when(this.userService.existsByUsername(anyString())).thenReturn(false);
        doNothing().when(this.roleService).addRoleToUser((UserAddRoleBindingModel) any());

        UserAddRoleBindingModel userAddRoleBindingModel = new UserAddRoleBindingModel();
        userAddRoleBindingModel.setUsername("janedoe");
        userAddRoleBindingModel.setRoleName(RoleName.ROOT_ADMIN);
        String content = (new ObjectMapper()).writeValueAsString(userAddRoleBindingModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/roles/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.roleController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"User with janedoe does not exist\"}")));
    }

    @Test
    public void testRemoveRoleToUser() throws Exception {
        when(this.userService.existsByUsername(anyString())).thenReturn(true);
        doNothing().when(this.roleService).deleteRoleToUser(anyString(), (RoleName) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/roles/delete");
        MockHttpServletRequestBuilder requestBuilder = deleteResult.param("roleName", String.valueOf(RoleName.ROOT_ADMIN))
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.roleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"Role deleted successfully\"}")));
    }

    @Test
    public void testRemoveRoleToUser2() throws Exception {
        when(this.userService.existsByUsername(anyString())).thenReturn(false);
        doNothing().when(this.roleService).deleteRoleToUser(anyString(), (RoleName) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/roles/delete");
        MockHttpServletRequestBuilder requestBuilder = deleteResult.param("roleName", String.valueOf(RoleName.ROOT_ADMIN))
                .param("username", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.roleController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"message\":\"User with foo does not exist\"}")));
    }
}

