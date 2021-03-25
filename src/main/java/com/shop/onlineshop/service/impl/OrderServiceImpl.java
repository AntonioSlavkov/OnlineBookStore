package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.repository.OrderRepository;
import com.shop.onlineshop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
}
