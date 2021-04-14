package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.entity.OrderEntity;
import com.shop.onlineshop.model.view.OrdersViewModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderViewMapper {

    OrdersViewModel orderEntityToOrderViewModel (OrderEntity orderEntity);

    List<OrdersViewModel> orderEntityToOrderViewModelList (List<OrderEntity> orderEntities);
}
