package com.shop.onlineshop.web;

import com.shop.onlineshop.model.view.RoleViewModel;
import com.shop.onlineshop.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
@CrossOrigin("*")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<?> getUserRoles (@RequestBody String username, RoleViewModel roleViewModel) {

        roleService.getUserRoles(username, roleViewModel);

        return ResponseEntity.ok().build();
    }
}
