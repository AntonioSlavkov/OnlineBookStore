package com.shop.onlineshop.web;

import com.shop.onlineshop.model.binding.RoleAddBindingModel;
import com.shop.onlineshop.model.binding.UserAddRoleBindingModel;
import com.shop.onlineshop.model.binding.UserDeleteRoleBindingModel;
import com.shop.onlineshop.model.view.RoleViewModel;
import com.shop.onlineshop.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
@CrossOrigin("*")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/all")
    public List<RoleViewModel> getUserRoles (@RequestBody UserAddRoleBindingModel userAddRoleBindingModel) {

        return roleService.getUserRoles(userAddRoleBindingModel);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRoleToUser (@RequestBody UserAddRoleBindingModel userAddRoleBindingModel) {

        roleService.addRoleToUser(userAddRoleBindingModel);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> removeRoleToUser (@RequestBody UserDeleteRoleBindingModel userDeleteRoleBindingModel) {

        roleService.deleteRoleToUser(userDeleteRoleBindingModel);

        return ResponseEntity.ok().build();
    }
}
