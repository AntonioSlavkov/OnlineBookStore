package com.shop.onlineshop.model.binding;

import com.shop.onlineshop.model.entity.RoleEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserAddBindingModel {

    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    @NotBlank(message = "Username must not be blank.")
    private String username;

    @NotBlank(message = "First name must not be blank.")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @Email
    @NotNull
    private String email;

    @Size(min = 10, max = 20, message = "Password must be between 10 and 20 characters")
    private String password;

}
