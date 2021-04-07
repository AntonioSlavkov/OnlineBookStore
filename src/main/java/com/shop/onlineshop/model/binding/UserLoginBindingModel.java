package com.shop.onlineshop.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginBindingModel {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
