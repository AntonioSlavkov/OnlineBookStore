package com.shop.onlineshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.onlineshop.mapper.CartViewMapper;
import com.shop.onlineshop.model.binding.AddBookToUserCart;
import com.shop.onlineshop.model.entity.CartEntity;
import com.shop.onlineshop.model.entity.RoleEntity;
import com.shop.onlineshop.model.entity.UserContactEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.view.BookViewModel;
import com.shop.onlineshop.model.view.CartViewModel;
import com.shop.onlineshop.repository.CartRepository;
import com.shop.onlineshop.service.BookService;
import com.shop.onlineshop.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CartServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class CartServiceImplTest {
    @MockBean
    private BookService bookService;

    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private CartServiceImpl cartServiceImpl;

    @MockBean
    private CartViewMapper cartViewMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testGetUserCart() {
        when(this.userService.existsByUsername(anyString())).thenReturn(true);
        ArrayList<CartViewModel> cartViewModelList = new ArrayList<CartViewModel>();
        when(this.cartViewMapper.cartEntityToCartViewModelList((List<CartEntity>) any())).thenReturn(cartViewModelList);
        when(this.cartRepository.findAll()).thenReturn(new ArrayList<CartEntity>());
        List<CartViewModel> actualUserCart = this.cartServiceImpl.getUserCart("janedoe");
        assertSame(cartViewModelList, actualUserCart);
        assertTrue(actualUserCart.isEmpty());
        verify(this.cartRepository).findAll();
        verify(this.cartViewMapper).cartEntityToCartViewModelList((List<CartEntity>) any());
        verify(this.userService).existsByUsername(anyString());
    }

    @Test
    public void testGetUserCart2() {
        when(this.userService.existsByUsername(anyString())).thenReturn(false);
        when(this.cartViewMapper.cartEntityToCartViewModelList((List<CartEntity>) any()))
                .thenReturn(new ArrayList<CartViewModel>());
        when(this.cartRepository.findAll()).thenReturn(new ArrayList<CartEntity>());
        assertThrows(UsernameNotFoundException.class, () -> this.cartServiceImpl.getUserCart("janedoe"));
        verify(this.userService).existsByUsername(anyString());
    }

    @Test
    public void testAddBookToUserCart() {
        when(this.userService.findUserByUsername(anyString())).thenThrow(new UsernameNotFoundException("Msg"));
        when(this.userService.existsByUsername(anyString())).thenReturn(true);
        assertThrows(UsernameNotFoundException.class,
                () -> this.cartServiceImpl.addBookToUserCart(new AddBookToUserCart()));
        verify(this.userService).existsByUsername(anyString());
        verify(this.userService).findUserByUsername(anyString());
    }

    @Test
    public void testAddBookToUserCart2() {
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
        when(this.userService.existsByUsername(anyString())).thenReturn(true);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(123L);
        cartEntity.setBookId(123L);
        cartEntity.setUserId(123L);
        when(this.cartRepository.save((CartEntity) any())).thenReturn(cartEntity);
        when(this.bookService.getBookById(anyLong())).thenReturn(new BookViewModel());

        AddBookToUserCart addBookToUserCart = new AddBookToUserCart();
        addBookToUserCart.setId(123L);
        this.cartServiceImpl.addBookToUserCart(addBookToUserCart);
        verify(this.bookService).getBookById(anyLong());
        verify(this.cartRepository).save((CartEntity) any());
        verify(this.userService).existsByUsername(anyString());
        verify(this.userService).findUserByUsername(anyString());
    }

    @Test
    public void testAddBookToUserCart3() {
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
        when(this.userService.existsByUsername(anyString())).thenReturn(false);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(123L);
        cartEntity.setBookId(123L);
        cartEntity.setUserId(123L);
        when(this.cartRepository.save((CartEntity) any())).thenReturn(cartEntity);
        when(this.bookService.getBookById(anyLong())).thenReturn(new BookViewModel());

        AddBookToUserCart addBookToUserCart = new AddBookToUserCart();
        addBookToUserCart.setId(123L);
        assertThrows(UsernameNotFoundException.class, () -> this.cartServiceImpl.addBookToUserCart(addBookToUserCart));
        verify(this.userService).existsByUsername(anyString());
    }

    @Test
    public void testDeleteUserCart() {
        doNothing().when(this.cartRepository).deleteByUserId((Long) any());
        this.cartServiceImpl.deleteUserCart(123L);
        verify(this.cartRepository).deleteByUserId((Long) any());
    }
}

