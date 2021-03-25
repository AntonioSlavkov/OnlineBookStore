package com.shop.onlineshop.web;

import com.shop.onlineshop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;
}
