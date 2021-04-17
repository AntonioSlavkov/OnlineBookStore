package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.mapper.OrderAddMapper;
import com.shop.onlineshop.mapper.OrderViewMapper;
import com.shop.onlineshop.model.binding.OrderAddBindingModel;
import com.shop.onlineshop.model.binding.OrderUpdateBindingModel;
import com.shop.onlineshop.model.entity.OrderEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.entity.enums.StatusName;
import com.shop.onlineshop.model.view.OrdersViewModel;
import com.shop.onlineshop.repository.OrderRepository;
import com.shop.onlineshop.repository.UserRepository;
import com.shop.onlineshop.service.CartService;
import com.shop.onlineshop.service.OrderService;
import com.shop.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderAddMapper orderAddMapper;
    private final OrderViewMapper orderViewMapper;
    private final CartService cartService;

    @Override
    @Transactional
    public void addOrder(OrderAddBindingModel orderAddBindingModel) {


        UserEntity user = userService
                .findUserByUsername(orderAddBindingModel.getUsername());

        OrderEntity order = orderAddMapper.orderAddBindingToOrderEntity(orderAddBindingModel);
        order.setUser(user);
        order.setStatus(StatusName.NEW);

        orderRepository.save(order);

        cartService.deleteUserCart(user.getId());
    }

    @Override
    public void updateOrder(OrderUpdateBindingModel orderUpdateBindingModel) {

        OrderEntity order = orderRepository
                .getOne(orderUpdateBindingModel.getId());

        order.setStatus(orderUpdateBindingModel.getStatusName());

        orderRepository.save(order);

    }

    @Override
    public List<OrdersViewModel> getAllOrders() {
        return orderViewMapper.orderEntityToOrderViewModelList(orderRepository.findAll());
    }

    @Override
    public OrdersViewModel getUserOrders(String username) {

        if (!userService.existsByUsername(username)) {
            throw new UsernameNotFoundException("User with " + username + " does not exist.");
        }

        UserEntity user = userService.findUserByUsername(username);

        OrderEntity userOrder = orderRepository.findByUserId(user.getId());

        return orderViewMapper.orderEntityToOrderViewModel(userOrder);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return orderRepository.existsById(id);
    }
}
