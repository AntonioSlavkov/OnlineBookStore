package com.shop.onlineshop.service;

import com.shop.onlineshop.model.binding.OrderAddBindingModel;
import com.shop.onlineshop.model.binding.OrderUpdateBindingModel;

public interface OrderService {
    void addOrder(OrderAddBindingModel orderAddBindingModel);

    void updateOrder(OrderUpdateBindingModel orderUpdateBindingModel);
}
