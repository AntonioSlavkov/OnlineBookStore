package com.shop.onlineshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.onlineshop.mapper.BookAddMapperImpl;
import com.shop.onlineshop.mapper.BookViewMapperImpl;
import com.shop.onlineshop.mapper.CartViewMapperImpl;
import com.shop.onlineshop.mapper.OrderAddMapper;
import com.shop.onlineshop.mapper.OrderAddMapperImpl;
import com.shop.onlineshop.mapper.OrderViewMapper;
import com.shop.onlineshop.mapper.OrderViewMapperImpl;
import com.shop.onlineshop.mapper.UserAddMapperImpl;
import com.shop.onlineshop.model.binding.OrderAddBindingModel;
import com.shop.onlineshop.model.binding.OrderUpdateBindingModel;
import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.entity.OrderEntity;
import com.shop.onlineshop.model.entity.RoleEntity;
import com.shop.onlineshop.model.entity.UserContactEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.entity.enums.StatusName;
import com.shop.onlineshop.model.view.OrdersViewModel;
import com.shop.onlineshop.repository.AuthorRepository;
import com.shop.onlineshop.repository.BookRepository;
import com.shop.onlineshop.repository.CartRepository;
import com.shop.onlineshop.repository.CategoryRepository;
import com.shop.onlineshop.repository.OrderRepository;
import com.shop.onlineshop.repository.RoleRepository;
import com.shop.onlineshop.repository.UserRepository;
import com.shop.onlineshop.service.CartService;
import com.shop.onlineshop.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OrderServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class OrderServiceImplTest {
    @MockBean
    private CartService cartService;

    @MockBean
    private OrderAddMapper orderAddMapper;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @MockBean
    private OrderViewMapper orderViewMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testAddOrder() {
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

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(userEntity);
        orderEntity.setStatus(StatusName.NEW);
        orderEntity.setId(123L);
        orderEntity.setBooks(new ArrayList<BookEntity>());
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save((OrderEntity) any())).thenReturn(orderEntity);

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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.<UserEntity>of(userEntity1));
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserAddMapperImpl userAddMapper = new UserAddMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, userAddMapper,
                new Argon2PasswordEncoder());
        CartRepository cartRepository = mock(CartRepository.class);
        doNothing().when(cartRepository).deleteByUserId((Long) any());
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        UserAddMapperImpl userAddMapper1 = new UserAddMapperImpl();
        UserServiceImpl userService1 = new UserServiceImpl(userRepository1, roleRepository1, userAddMapper1,
                new Argon2PasswordEncoder());
        AuthorServiceImpl authorService = new AuthorServiceImpl(mock(AuthorRepository.class), null, null);
        CategoryServiceImpl categoryService = new CategoryServiceImpl(mock(CategoryRepository.class), null, null);
        BookRepository bookRepository = mock(BookRepository.class);
        BookViewMapperImpl bookViewMapper = new BookViewMapperImpl();
        BookAddMapperImpl bookAddMapper = new BookAddMapperImpl();
        BookServiceImpl bookService = new BookServiceImpl(authorService, categoryService, bookRepository, bookViewMapper,
                bookAddMapper);
        CartServiceImpl cartService = new CartServiceImpl(userService1, cartRepository, bookService,
                new CartViewMapperImpl());
        OrderAddMapperImpl orderAddMapper = new OrderAddMapperImpl();
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, userService, orderAddMapper,
                new OrderViewMapperImpl(), cartService);
        orderServiceImpl.addOrder(new OrderAddBindingModel());
        verify(cartRepository).deleteByUserId((Long) any());
        verify(orderRepository).save((OrderEntity) any());
        verify(userRepository).findByUsername(anyString());
    }

    @Test
    public void testAddOrder2() {
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

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(userEntity);
        orderEntity.setStatus(StatusName.NEW);
        orderEntity.setId(123L);
        orderEntity.setBooks(new ArrayList<BookEntity>());
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save((OrderEntity) any())).thenReturn(orderEntity);

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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.<UserEntity>of(userEntity1));
        RoleRepository roleRepository = mock(RoleRepository.class);
        UserAddMapperImpl userAddMapper = new UserAddMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, userAddMapper,
                new Argon2PasswordEncoder());
        CartRepository cartRepository = mock(CartRepository.class);
        doNothing().when(cartRepository).deleteByUserId((Long) any());
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        UserAddMapperImpl userAddMapper1 = new UserAddMapperImpl();
        UserServiceImpl userService1 = new UserServiceImpl(userRepository1, roleRepository1, userAddMapper1,
                new Argon2PasswordEncoder());
        AuthorServiceImpl authorService = new AuthorServiceImpl(mock(AuthorRepository.class), null, null);
        CategoryServiceImpl categoryService = new CategoryServiceImpl(mock(CategoryRepository.class), null, null);
        BookRepository bookRepository = mock(BookRepository.class);
        BookViewMapperImpl bookViewMapper = new BookViewMapperImpl();
        BookAddMapperImpl bookAddMapper = new BookAddMapperImpl();
        BookServiceImpl bookService = new BookServiceImpl(authorService, categoryService, bookRepository, bookViewMapper,
                bookAddMapper);
        CartServiceImpl cartService = new CartServiceImpl(userService1, cartRepository, bookService,
                new CartViewMapperImpl());
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, userService, null,
                new OrderViewMapperImpl(), cartService);
        OrderAddBindingModel orderAddBindingModel = mock(OrderAddBindingModel.class);
        when(orderAddBindingModel.getUsername()).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> orderServiceImpl.addOrder(orderAddBindingModel));
        verify(orderAddBindingModel).getUsername();
    }

    @Test
    public void testUpdateOrder() {
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

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(userEntity);
        orderEntity.setStatus(StatusName.NEW);
        orderEntity.setId(123L);
        orderEntity.setBooks(new ArrayList<BookEntity>());

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

        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setUser(userEntity1);
        orderEntity1.setStatus(StatusName.NEW);
        orderEntity1.setId(123L);
        orderEntity1.setBooks(new ArrayList<BookEntity>());
        when(this.orderRepository.save((OrderEntity) any())).thenReturn(orderEntity1);
        when(this.orderRepository.getOne((Long) any())).thenReturn(orderEntity);
        this.orderServiceImpl.updateOrder(new OrderUpdateBindingModel());
        verify(this.orderRepository).save((OrderEntity) any());
        verify(this.orderRepository).getOne((Long) any());
    }

    @Test
    public void testGetAllOrders() {
        ArrayList<OrdersViewModel> ordersViewModelList = new ArrayList<OrdersViewModel>();
        when(this.orderViewMapper.orderEntityToOrderViewModelList((List<OrderEntity>) any()))
                .thenReturn(ordersViewModelList);
        when(this.orderRepository.findAll()).thenReturn(new ArrayList<OrderEntity>());
        List<OrdersViewModel> actualAllOrders = this.orderServiceImpl.getAllOrders();
        assertSame(ordersViewModelList, actualAllOrders);
        assertTrue(actualAllOrders.isEmpty());
        verify(this.orderRepository).findAll();
        verify(this.orderViewMapper).orderEntityToOrderViewModelList((List<OrderEntity>) any());
    }

    @Test
    public void testGetUserOrders() {
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
        OrdersViewModel ordersViewModel = new OrdersViewModel();
        when(this.orderViewMapper.orderEntityToOrderViewModel((OrderEntity) any())).thenReturn(ordersViewModel);

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

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(userEntity1);
        orderEntity.setStatus(StatusName.NEW);
        orderEntity.setId(123L);
        orderEntity.setBooks(new ArrayList<BookEntity>());
        when(this.orderRepository.findByUserId((Long) any())).thenReturn(orderEntity);
        assertSame(ordersViewModel, this.orderServiceImpl.getUserOrders("janedoe"));
        verify(this.orderRepository).findByUserId((Long) any());
        verify(this.orderViewMapper).orderEntityToOrderViewModel((OrderEntity) any());
        verify(this.userService).existsByUsername(anyString());
        verify(this.userService).findUserByUsername(anyString());
    }

    @Test
    public void testGetUserOrders2() {
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
        when(this.orderViewMapper.orderEntityToOrderViewModel((OrderEntity) any())).thenReturn(new OrdersViewModel());

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

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(userEntity1);
        orderEntity.setStatus(StatusName.NEW);
        orderEntity.setId(123L);
        orderEntity.setBooks(new ArrayList<BookEntity>());
        when(this.orderRepository.findByUserId((Long) any())).thenReturn(orderEntity);
        assertThrows(UsernameNotFoundException.class, () -> this.orderServiceImpl.getUserOrders("janedoe"));
        verify(this.userService).existsByUsername(anyString());
    }
}

