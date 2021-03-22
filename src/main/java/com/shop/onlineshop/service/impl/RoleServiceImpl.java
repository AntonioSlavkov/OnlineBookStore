package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.repository.RoleRepository;
import com.shop.onlineshop.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
}
