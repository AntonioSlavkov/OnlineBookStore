package com.shop.onlineshop.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CategoryAddBindingModel {

    private List<String> categories;
}
