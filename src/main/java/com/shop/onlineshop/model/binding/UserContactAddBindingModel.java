package com.shop.onlineshop.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserContactAddBindingModel {

    private String username;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String city;
    @NotBlank
    private String address;
}
