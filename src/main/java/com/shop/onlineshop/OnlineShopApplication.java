package com.shop.onlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopApplication.class, args);
    }

    //TODO Make MessageDTOs for the return of the responses on the add and delete methods
    //TODO add validation to category and author
    //TODO implement roles and orders
    // TODO finish security
    //TODO make unique email and username
}
