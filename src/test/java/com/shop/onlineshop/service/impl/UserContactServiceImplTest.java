package com.shop.onlineshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.onlineshop.exception.UserContactNotFoundException;
import com.shop.onlineshop.mapper.UserContactAddMapper;
import com.shop.onlineshop.mapper.UserContactViewMapper;
import com.shop.onlineshop.model.binding.UserContactAddBindingModel;
import com.shop.onlineshop.model.entity.RoleEntity;
import com.shop.onlineshop.model.entity.UserContactEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.view.UserContactViewModel;
import com.shop.onlineshop.repository.UserContactRepository;
import com.shop.onlineshop.repository.UserRepository;
import com.shop.onlineshop.service.UserService;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserContactServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class UserContactServiceImplTest {
    @MockBean
    private UserContactAddMapper userContactAddMapper;

    @MockBean
    private UserContactRepository userContactRepository;

    @Autowired
    private UserContactServiceImpl userContactServiceImpl;

    @MockBean
    private UserContactViewMapper userContactViewMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Test
    public void testGetUserContacts() {
        UserContactEntity userContactEntity = new UserContactEntity();
        userContactEntity.setId(123L);
        userContactEntity.setCity("Oxford");
        userContactEntity.setPhoneNumber("4105551212");
        userContactEntity.setAddress("42 Main St");

        UserEntity userEntity = new UserEntity();
        userEntity.setLastName("Doe");
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setPassword("iloveyou");
        userEntity.setRoles(new ArrayList<RoleEntity>());
        userEntity.setUsername("janedoe");
        userEntity.setId(123L);
        userEntity.setUserContactEntity(userContactEntity);
        userEntity.setFirstName("Jane");
        when(this.userService.findUserByUsername(anyString())).thenReturn(userEntity);
        UserContactViewModel userContactViewModel = new UserContactViewModel();
        when(this.userContactViewMapper.userContactEntityToUserContactViewModel((UserContactEntity) any()))
                .thenReturn(userContactViewModel);
        assertSame(userContactViewModel, this.userContactServiceImpl.getUserContacts("janedoe"));
        verify(this.userContactViewMapper).userContactEntityToUserContactViewModel((UserContactEntity) any());
        verify(this.userService).findUserByUsername(anyString());
    }

//    @Test
//    public void testUpdateUserContacts() {
//        UserContactEntity userContactEntity = new UserContactEntity();
//        userContactEntity.setId(123L);
//        userContactEntity.setCity("Oxford");
//        userContactEntity.setPhoneNumber("4105551212");
//        userContactEntity.setAddress("42 Main St");
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setLastName("Doe");
//        userEntity.setEmail("jane.doe@example.org");
//        userEntity.setPassword("iloveyou");
//        userEntity.setRoles(new ArrayList<RoleEntity>());
//        userEntity.setUsername("janedoe");
//        userEntity.setId(123L);
//        userEntity.setUserContactEntity(userContactEntity);
//        userEntity.setFirstName("Jane");
//        when(this.userService.findUserByUsername(anyString())).thenReturn(userEntity);
//
//        UserContactEntity userContactEntity1 = new UserContactEntity();
//        userContactEntity1.setId(123L);
//        userContactEntity1.setCity("Oxford");
//        userContactEntity1.setPhoneNumber("4105551212");
//        userContactEntity1.setAddress("42 Main St");
//
//        UserEntity userEntity1 = new UserEntity();
//        userEntity1.setLastName("Doe");
//        userEntity1.setEmail("jane.doe@example.org");
//        userEntity1.setPassword("iloveyou");
//        userEntity1.setRoles(new ArrayList<RoleEntity>());
//        userEntity1.setUsername("janedoe");
//        userEntity1.setId(123L);
//        userEntity1.setUserContactEntity(userContactEntity1);
//        userEntity1.setFirstName("Jane");
//        when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity1);
//
//        UserContactEntity userContactEntity2 = new UserContactEntity();
//        userContactEntity2.setId(123L);
//        userContactEntity2.setCity("Oxford");
//        userContactEntity2.setPhoneNumber("4105551212");
//        userContactEntity2.setAddress("42 Main St");
//        Optional<UserContactEntity> ofResult = Optional.<UserContactEntity>of(userContactEntity2);
//        when(this.userContactRepository.findById((Long) any())).thenReturn(ofResult);
//
//        UserContactEntity userContactEntity3 = new UserContactEntity();
//        userContactEntity3.setId(123L);
//        userContactEntity3.setCity("Oxford");
//        userContactEntity3.setPhoneNumber("4105551212");
//        userContactEntity3.setAddress("42 Main St");
//        when(this.userContactAddMapper.userContactAddBindingToUserContactEntity((UserContactAddBindingModel) any()))
//                .thenReturn(userContactEntity3);
//        this.userContactServiceImpl.updateUserContacts(new UserContactAddBindingModel());
//        verify(this.userContactAddMapper).userContactAddBindingToUserContactEntity((UserContactAddBindingModel) any());
//        verify(this.userContactRepository).findById((Long) any());
//        verify(this.userRepository).save((UserEntity) any());
//        verify(this.userService).findUserByUsername(anyString());
//    }

//    @Test
//    public void testUpdateUserContacts2() {
//        UserContactEntity userContactEntity = new UserContactEntity();
//        userContactEntity.setId(123L);
//        userContactEntity.setCity("Oxford");
//        userContactEntity.setPhoneNumber("4105551212");
//        userContactEntity.setAddress("42 Main St");
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setLastName("Doe");
//        userEntity.setEmail("jane.doe@example.org");
//        userEntity.setPassword("iloveyou");
//        userEntity.setRoles(new ArrayList<RoleEntity>());
//        userEntity.setUsername("janedoe");
//        userEntity.setId(123L);
//        userEntity.setUserContactEntity(userContactEntity);
//        userEntity.setFirstName("Jane");
//        when(this.userService.findUserByUsername(anyString())).thenReturn(userEntity);
//
//        UserContactEntity userContactEntity1 = new UserContactEntity();
//        userContactEntity1.setId(123L);
//        userContactEntity1.setCity("Oxford");
//        userContactEntity1.setPhoneNumber("4105551212");
//        userContactEntity1.setAddress("42 Main St");
//
//        UserEntity userEntity1 = new UserEntity();
//        userEntity1.setLastName("Doe");
//        userEntity1.setEmail("jane.doe@example.org");
//        userEntity1.setPassword("iloveyou");
//        userEntity1.setRoles(new ArrayList<RoleEntity>());
//        userEntity1.setUsername("janedoe");
//        userEntity1.setId(123L);
//        userEntity1.setUserContactEntity(userContactEntity1);
//        userEntity1.setFirstName("Jane");
//        when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity1);
//        when(this.userContactRepository.findById((Long) any())).thenReturn(Optional.<UserContactEntity>empty());
//
//        UserContactEntity userContactEntity2 = new UserContactEntity();
//        userContactEntity2.setId(123L);
//        userContactEntity2.setCity("Oxford");
//        userContactEntity2.setPhoneNumber("4105551212");
//        userContactEntity2.setAddress("42 Main St");
//        when(this.userContactAddMapper.userContactAddBindingToUserContactEntity((UserContactAddBindingModel) any()))
//                .thenReturn(userContactEntity2);
//        assertThrows(UserContactNotFoundException.class,
//                () -> this.userContactServiceImpl.updateUserContacts(new UserContactAddBindingModel()));
//        verify(this.userContactRepository).findById((Long) any());
//        verify(this.userService).findUserByUsername(anyString());
//    }
}

