package com.shop.onlineshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.onlineshop.exception.InvalidRoleException;
import com.shop.onlineshop.mapper.RoleViewMapper;
import com.shop.onlineshop.model.binding.UserAddRoleBindingModel;
import com.shop.onlineshop.model.entity.RoleEntity;
import com.shop.onlineshop.model.entity.UserContactEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.entity.enums.RoleName;
import com.shop.onlineshop.model.view.RoleViewModel;
import com.shop.onlineshop.repository.RoleRepository;
import com.shop.onlineshop.repository.UserRepository;
import com.shop.onlineshop.service.UserService;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RoleServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class RoleServiceImplTest {
    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @MockBean
    private RoleViewMapper roleViewMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Test
    public void testGetUserRoles() {
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
        assertTrue(this.roleServiceImpl.getUserRoles("janedoe").isEmpty());
        verify(this.userService).findUserByUsername(anyString());
    }

    @Test
    public void testGetUserRoles2() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleName.ROOT_ADMIN);
        roleEntity.setId(123L);

        ArrayList<RoleEntity> roleEntityList = new ArrayList<RoleEntity>();
        roleEntityList.add(roleEntity);

        UserContactEntity userContactEntity = new UserContactEntity();
        userContactEntity.setId(123L);
        userContactEntity.setCity("Oxford");
        userContactEntity.setPhoneNumber("4105551212");
        userContactEntity.setAddress("42 Main St");

        UserEntity userEntity = new UserEntity();
        userEntity.setLastName("Doe");
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setPassword("iloveyou");
        userEntity.setRoles(roleEntityList);
        userEntity.setUsername("janedoe");
        userEntity.setId(123L);
        userEntity.setUserContactEntity(userContactEntity);
        userEntity.setFirstName("Jane");
        when(this.userService.findUserByUsername(anyString())).thenReturn(userEntity);
        when(this.roleViewMapper.roleEntityToRoleViewMapper((RoleEntity) any())).thenReturn(new RoleViewModel());
        assertEquals(1, this.roleServiceImpl.getUserRoles("janedoe").size());
        verify(this.roleViewMapper).roleEntityToRoleViewMapper((RoleEntity) any());
        verify(this.userService).findUserByUsername(anyString());
    }

    @Test
    public void testAddRoleToUser() {
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

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleName.ROOT_ADMIN);
        roleEntity.setId(123L);
        Optional<RoleEntity> ofResult = Optional.<RoleEntity>of(roleEntity);
        when(this.roleRepository.findByRole((RoleName) any())).thenReturn(ofResult);
        assertThrows(UsernameNotFoundException.class,
                () -> this.roleServiceImpl.addRoleToUser(new UserAddRoleBindingModel()));
        verify(this.roleRepository).findByRole((RoleName) any());
//        verify(this.userService).findUserByUsername(anyString());
    }

    @Test
    public void testAddRoleToUser2() {
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
        when(this.roleRepository.findByRole((com.shop.onlineshop.model.entity.enums.RoleName) any()))
                .thenReturn(Optional.<RoleEntity>empty());
        assertThrows(InvalidRoleException.class, () -> this.roleServiceImpl.addRoleToUser(new UserAddRoleBindingModel()));
        verify(this.roleRepository).findByRole((com.shop.onlineshop.model.entity.enums.RoleName) any());
    }

    @Test
    public void testAddRoleToUser3() {
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

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleName.ROOT_ADMIN);
        roleEntity.setId(123L);
        Optional<RoleEntity> ofResult = Optional.<RoleEntity>of(roleEntity);
        when(this.roleRepository.findByRole((RoleName) any())).thenReturn(ofResult);

        UserAddRoleBindingModel userAddRoleBindingModel = new UserAddRoleBindingModel();
        userAddRoleBindingModel.setRoleName(RoleName.ROOT_ADMIN);
        assertThrows(UsernameNotFoundException.class, () -> this.roleServiceImpl.addRoleToUser(userAddRoleBindingModel));
        verify(this.roleRepository).findByRole((RoleName) any());
//        verify(this.userService).findUserByUsername(anyString());
    }

    @Test
    public void testAddRoleToUser4() {
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

        UserContactEntity userContactEntity1 = new UserContactEntity();
        userContactEntity1.setId(123L);
        userContactEntity1.setCity("Oxford");
        userContactEntity1.setPhoneNumber("4105551212");
        userContactEntity1.setAddress("42 Main St");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setLastName("Doe");
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setPassword("iloveyou");
        userEntity1.setRoles(new ArrayList<RoleEntity>());
        userEntity1.setUsername("janedoe");
        userEntity1.setId(123L);
        userEntity1.setUserContactEntity(userContactEntity1);
        userEntity1.setFirstName("Jane");
        when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity1);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleName.ROOT_ADMIN);
        roleEntity.setId(123L);
        Optional<RoleEntity> ofResult = Optional.<RoleEntity>of(roleEntity);
        when(this.roleRepository.findByRole((RoleName) any())).thenReturn(ofResult);

        UserAddRoleBindingModel userAddRoleBindingModel = new UserAddRoleBindingModel();
        userAddRoleBindingModel.setUsername("janedoe");
        this.roleServiceImpl.addRoleToUser(userAddRoleBindingModel);
        verify(this.roleRepository).findByRole((RoleName) any());
        verify(this.userRepository).save((UserEntity) any());
        verify(this.userService).findUserByUsername(anyString());
    }

    @Test
    public void testDeleteRoleToUser() {
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

        UserContactEntity userContactEntity1 = new UserContactEntity();
        userContactEntity1.setId(123L);
        userContactEntity1.setCity("Oxford");
        userContactEntity1.setPhoneNumber("4105551212");
        userContactEntity1.setAddress("42 Main St");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setLastName("Doe");
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setPassword("iloveyou");
        userEntity1.setRoles(new ArrayList<RoleEntity>());
        userEntity1.setUsername("janedoe");
        userEntity1.setId(123L);
        userEntity1.setUserContactEntity(userContactEntity1);
        userEntity1.setFirstName("Jane");
        when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity1);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleName.ROOT_ADMIN);
        roleEntity.setId(123L);
        Optional<RoleEntity> ofResult = Optional.<RoleEntity>of(roleEntity);
        when(this.roleRepository.findByRole((RoleName) any())).thenReturn(ofResult);
        this.roleServiceImpl.deleteRoleToUser("janedoe", RoleName.ROOT_ADMIN);
        verify(this.roleRepository).findByRole((RoleName) any());
        verify(this.userRepository).save((UserEntity) any());
        verify(this.userService).findUserByUsername(anyString());
    }

    @Test
    public void testDeleteRoleToUser2() {
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
        userEntity.setUsername("Username");
        userEntity.setId(123L);
        userEntity.setUserContactEntity(userContactEntity);
        userEntity.setFirstName("Jane");
        when(this.userService.findUserByUsername(anyString())).thenReturn(userEntity);

        UserContactEntity userContactEntity1 = new UserContactEntity();
        userContactEntity1.setId(123L);
        userContactEntity1.setCity("Oxford");
        userContactEntity1.setPhoneNumber("4105551212");
        userContactEntity1.setAddress("42 Main St");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setLastName("Doe");
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setPassword("iloveyou");
        userEntity1.setRoles(new ArrayList<RoleEntity>());
        userEntity1.setUsername("janedoe");
        userEntity1.setId(123L);
        userEntity1.setUserContactEntity(userContactEntity1);
        userEntity1.setFirstName("Jane");
        when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity1);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleName.ROOT_ADMIN);
        roleEntity.setId(123L);
        Optional<RoleEntity> ofResult = Optional.<RoleEntity>of(roleEntity);
        when(this.roleRepository.findByRole((RoleName) any())).thenReturn(ofResult);
        assertThrows(UsernameNotFoundException.class,
                () -> this.roleServiceImpl.deleteRoleToUser("janedoe", RoleName.ROOT_ADMIN));
        verify(this.roleRepository).findByRole((RoleName) any());
        verify(this.userService).findUserByUsername(anyString());
    }

    @Test
    public void testDeleteRoleToUser3() {
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

        UserContactEntity userContactEntity1 = new UserContactEntity();
        userContactEntity1.setId(123L);
        userContactEntity1.setCity("Oxford");
        userContactEntity1.setPhoneNumber("4105551212");
        userContactEntity1.setAddress("42 Main St");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setLastName("Doe");
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setPassword("iloveyou");
        userEntity1.setRoles(new ArrayList<RoleEntity>());
        userEntity1.setUsername("janedoe");
        userEntity1.setId(123L);
        userEntity1.setUserContactEntity(userContactEntity1);
        userEntity1.setFirstName("Jane");
        when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity1);
        when(this.roleRepository.findByRole((RoleName) any())).thenReturn(Optional.<RoleEntity>empty());
        assertThrows(InvalidRoleException.class,
                () -> this.roleServiceImpl.deleteRoleToUser("janedoe", RoleName.ROOT_ADMIN));
        verify(this.roleRepository).findByRole((RoleName) any());
    }
}

