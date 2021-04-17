package com.shop.onlineshop.service;

import com.shop.onlineshop.model.binding.OrderAddBindingModel;
import com.shop.onlineshop.model.binding.OrderUpdateBindingModel;
import com.shop.onlineshop.model.view.OrdersViewModel;

import java.util.List;

public interface OrderService {
    void addOrder(OrderAddBindingModel orderAddBindingModel);

    void updateOrder(OrderUpdateBindingModel orderUpdateBindingModel);

    List<OrdersViewModel> getAllOrders();

    OrdersViewModel getUserOrders(String username);

    void deleteById(Long id);

    boolean existById (Long id);
}
