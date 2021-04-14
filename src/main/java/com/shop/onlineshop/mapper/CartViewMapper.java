package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.entity.CartEntity;
import com.shop.onlineshop.model.view.CartViewModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartViewMapper {

    List<CartViewModel> cartEntityToCartViewModelList (List<CartEntity> cartEntities);

    CartViewModel cartEntityToCartViewModel (CartEntity cartEntity);

}
