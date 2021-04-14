package com.shop.onlineshop.model.binding;

import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderAddBindingModel {

    private String username;
    private List<BookEntity> books;
}
