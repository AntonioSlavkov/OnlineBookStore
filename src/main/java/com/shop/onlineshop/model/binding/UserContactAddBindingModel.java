package com.shop.onlineshop.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class UserContactAddBindingModel {

    private String username;
    private String phoneNumber;
    private String city;
    private String address;
}
