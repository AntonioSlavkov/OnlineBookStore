package com.shop.onlineshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.onlineshop.mapper.UserAddMapper;
import com.shop.onlineshop.model.binding.UserAddBindingModel;
import com.shop.onlineshop.model.entity.RoleEntity;
import com.shop.onlineshop.model.entity.UserContactEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.entity.enums.RoleName;
import com.shop.onlineshop.repository.RoleRepository;
import com.shop.onlineshop.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserAddMapper userAddMapper;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    public void testRegisterUser() {
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
        when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity);

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
        when(this.userAddMapper.userAddBindingToUserEntity((UserAddBindingModel) any())).thenReturn(userEntity1);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleName.ROOT_ADMIN);
        roleEntity.setId(123L);
        Optional<RoleEntity> ofResult = Optional.<RoleEntity>of(roleEntity);
        when(this.roleRepository.findByRole((RoleName) any())).thenReturn(ofResult);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("foo");
        this.userServiceImpl.registerUser(new UserAddBindingModel());
        verify(this.passwordEncoder).encode((CharSequence) any());
        verify(this.roleRepository).findByRole((RoleName) any());
        verify(this.userAddMapper).userAddBindingToUserEntity((UserAddBindingModel) any());
        verify(this.userRepository).save((UserEntity) any());
    }

    @Test
    public void testRegisterUser2() {
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
        when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity);

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
        when(this.userAddMapper.userAddBindingToUserEntity((UserAddBindingModel) any())).thenReturn(userEntity1);
        when(this.roleRepository.findByRole((com.shop.onlineshop.model.entity.enums.RoleName) any()))
                .thenReturn(Optional.<RoleEntity>empty());
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("foo");
        assertThrows(IllegalStateException.class, () -> this.userServiceImpl.registerUser(new UserAddBindingModel()));
        verify(this.passwordEncoder).encode((CharSequence) any());
        verify(this.roleRepository).findByRole((com.shop.onlineshop.model.entity.enums.RoleName) any());
        verify(this.userAddMapper).userAddBindingToUserEntity((UserAddBindingModel) any());
    }

    @Test
    public void testFindUserByUsername() {
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
        Optional<UserEntity> ofResult = Optional.<UserEntity>of(userEntity);
        when(this.userRepository.findByUsername(anyString())).thenReturn(ofResult);
        assertSame(userEntity, this.userServiceImpl.findUserByUsername("janedoe"));
        verify(this.userRepository).findByUsername(anyString());
    }

    @Test
    public void testFindUserByUsername2() {
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.<UserEntity>empty());
        assertThrows(UsernameNotFoundException.class, () -> this.userServiceImpl.findUserByUsername("janedoe"));
        verify(this.userRepository).findByUsername(anyString());
    }

    @Test
    public void testExistsByUsername() {
        when(this.userRepository.existsUserEntityByUsername(anyString())).thenReturn(true);
        assertTrue(this.userServiceImpl.existsByUsername("janedoe"));
        verify(this.userRepository).existsUserEntityByUsername(anyString());
    }

    @Test
    public void testExistsByEmail() {
        when(this.userRepository.existsUserEntityByEmail(anyString())).thenReturn(true);
        assertTrue(this.userServiceImpl.existsByEmail("jane.doe@example.org"));
        verify(this.userRepository).existsUserEntityByEmail(anyString());
    }
}

