package com.shop.onlineshop.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class UserContactViewModel {


    private Long phoneNumber;
    private String city;
    private String address;
    private Long postalCode;
}
