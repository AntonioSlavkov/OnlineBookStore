package com.shop.onlineshop.web;

import com.shop.onlineshop.model.binding.UserAddRoleBindingModel;
import com.shop.onlineshop.model.entity.enums.RoleName;
import com.shop.onlineshop.model.message.MessageDto;
import com.shop.onlineshop.model.view.RoleViewModel;
import com.shop.onlineshop.service.RoleService;
import com.shop.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    @GetMapping("/all")
    public List<RoleViewModel> getUserRoles(@RequestParam String username) {

        return roleService.getUserRoles(username);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRoleToUser(@RequestBody UserAddRoleBindingModel userAddRoleBindingModel) {

        if (userAddRoleBindingModel.getUsername().isEmpty()) {
            return ResponseEntity.status(404).body(new MessageDto("Please provide a username"));
        }

        if (!userService.existsByUsername(userAddRoleBindingModel.getUsername())) {
            return ResponseEntity.status(404).body(
                    new MessageDto("User with " + userAddRoleBindingModel.getUsername() + " does not exist"));
        }

        roleService.addRoleToUser(userAddRoleBindingModel);

        return ResponseEntity.ok().body(new MessageDto("Role Added successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> removeRoleToUser(@RequestParam String username, @RequestParam RoleName roleName) {

        if (username.isBlank()) {
            return ResponseEntity.status(400).body(new MessageDto("Please provide a username"));
        }

        if (!userService.existsByUsername(username)) {
            return ResponseEntity.status(404).body(new MessageDto("User with " + username + " does not exist"));
        }

        roleService.deleteRoleToUser(username, roleName);

        return ResponseEntity.ok().body(new MessageDto("Role deleted successfully"));
    }
}
