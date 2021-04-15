package com.shop.onlineshop.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.shop.onlineshop.model.entity.RoleEntity;
import com.shop.onlineshop.model.entity.UserContactEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.entity.enums.RoleName;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

public class UserDetailsImplTest {
    @Test
    public void testBuild() {
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
        UserDetailsImpl actualBuildResult = UserDetailsImpl.build(userEntity);
        assertEquals("janedoe", actualBuildResult.getUsername());
        assertEquals("iloveyou", actualBuildResult.getPassword());
    }

    @Test
    public void testBuild2() {
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
        UserDetailsImpl actualBuildResult = UserDetailsImpl.build(userEntity);
        assertEquals("iloveyou", actualBuildResult.getPassword());
        assertEquals("janedoe", actualBuildResult.getUsername());
        assertEquals("ROOT_ADMIN",
                ((ArrayList<? extends GrantedAuthority>) actualBuildResult.getAuthorities()).get(0).getAuthority());
    }

    @Test
    public void testBuild3() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(RoleName.ROOT_ADMIN);
        roleEntity.setId(123L);

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setRole(RoleName.ROOT_ADMIN);
        roleEntity1.setId(123L);

        ArrayList<RoleEntity> roleEntityList = new ArrayList<RoleEntity>();
        roleEntityList.add(roleEntity1);
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
        UserDetailsImpl actualBuildResult = UserDetailsImpl.build(userEntity);
        assertEquals("iloveyou", actualBuildResult.getPassword());
        assertEquals("janedoe", actualBuildResult.getUsername());
        assertEquals("ROOT_ADMIN",
                ((ArrayList<? extends GrantedAuthority>) actualBuildResult.getAuthorities()).get(1).toString());
        assertEquals("ROOT_ADMIN",
                ((ArrayList<? extends GrantedAuthority>) actualBuildResult.getAuthorities()).get(0).toString());
    }
}

