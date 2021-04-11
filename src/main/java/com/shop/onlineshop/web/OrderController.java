package com.shop.onlineshop.web;

import com.shop.onlineshop.model.binding.OrderAddBindingModel;
import com.shop.onlineshop.model.binding.OrderUpdateBindingModel;
import com.shop.onlineshop.model.message.MessageDto;
import com.shop.onlineshop.model.view.OrdersViewModel;
import com.shop.onlineshop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/all")
    public OrdersViewModel getAllOrders () {
        return null;
    }

    @GetMapping("/user")
    public OrdersViewModel getOrdersByUsername (@RequestParam String username) {
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addOrder (@RequestBody OrderAddBindingModel orderAddBindingModel) {

        orderService.addOrder(orderAddBindingModel);
        return ResponseEntity.ok().body(new MessageDto("Orders successfully added"));
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateOrder (@RequestBody OrderUpdateBindingModel orderUpdateBindingModel) {

        orderService.updateOrder(orderUpdateBindingModel);
        return ResponseEntity.ok().body(new MessageDto("Orders successfully updated"));
    }
}
