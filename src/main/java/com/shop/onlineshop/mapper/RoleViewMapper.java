package com.shop.onlineshop.mapper;

import com.shop.onlineshop.model.entity.RoleEntity;
import com.shop.onlineshop.model.view.RoleViewModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleViewMapper {

    List<RoleViewModel> roleEntityListToRoleViewMapperList (List<RoleEntity> roleEntities);

    RoleViewModel roleEntityToRoleViewMapper (RoleEntity roleEntities);


}
