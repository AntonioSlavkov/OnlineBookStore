package com.shop.onlineshop.init;

import com.shop.onlineshop.repository.RoleRepository;
import com.shop.onlineshop.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;
    private final RoleService roleService;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        roleService.initRoles();
        userService.seedUser();
    }
}
