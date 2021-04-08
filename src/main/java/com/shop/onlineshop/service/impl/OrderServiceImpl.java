package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.mapper.OrderAddMapper;
import com.shop.onlineshop.model.binding.OrderAddBindingModel;
import com.shop.onlineshop.model.binding.OrderUpdateBindingModel;
import com.shop.onlineshop.model.entity.OrderEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.entity.enums.StatusName;
import com.shop.onlineshop.repository.OrderRepository;
import com.shop.onlineshop.repository.UserRepository;
import com.shop.onlineshop.service.OrderService;
import com.shop.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderAddMapper orderAddMapper;
    private final UserRepository userRepository;

    @Override
    public void addOrder(OrderAddBindingModel orderAddBindingModel) {

        UserEntity user = userService
                .findUserByUsername(orderAddBindingModel.getUser().getUsername());

        OrderEntity order = orderAddMapper.orderAddBindingToOrderEntity(orderAddBindingModel);
        order.setUser(user);
        order.setStatus(StatusName.NEW);

        orderRepository.save(order);
    }

    @Override
    public void updateOrder(OrderUpdateBindingModel orderUpdateBindingModel) {

        OrderEntity order = orderRepository
                .getOne(orderUpdateBindingModel.getId());

        order.setStatus(orderUpdateBindingModel.getStatusName());

        orderRepository.save(order);

    }
}
