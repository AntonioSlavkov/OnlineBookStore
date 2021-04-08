package com.shop.onlineshop.model.binding;

import com.shop.onlineshop.model.entity.enums.StatusName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderUpdateBindingModel {

    private Long id;
    private StatusName statusName;
}
