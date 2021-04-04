package com.shop.onlineshop.web;

import com.shop.onlineshop.model.binding.RoleAddBindingModel;
import com.shop.onlineshop.model.binding.UserAddRoleBindingModel;
import com.shop.onlineshop.model.binding.UserDeleteRoleBindingModel;
import com.shop.onlineshop.model.entity.enums.RoleName;
import com.shop.onlineshop.model.message.MessageDto;
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
    public List<RoleViewModel> getUserRoles (@RequestParam String username) {

        return roleService.getUserRoles(username);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRoleToUser (@RequestBody UserAddRoleBindingModel userAddRoleBindingModel) {

        roleService.addRoleToUser(userAddRoleBindingModel);

        return ResponseEntity.ok().body(new MessageDto("Role Added successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> removeRoleToUser (@RequestParam String username, @RequestParam RoleName roleName) {

        roleService.deleteRoleToUser(username, roleName);

        return ResponseEntity.ok().body(new MessageDto("Role deleted successfully"));
    }
}
