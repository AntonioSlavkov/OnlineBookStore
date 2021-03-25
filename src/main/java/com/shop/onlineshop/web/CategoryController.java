package com.shop.onlineshop.web;

import com.shop.onlineshop.model.binding.CategoryAddBindingModel;
import com.shop.onlineshop.model.view.CategoryViewModel;
import com.shop.onlineshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryViewModel> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/category/{id}")
    public CategoryViewModel getCategoryById (@PathVariable long id) {

        return categoryService.getCategoryById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryAddBindingModel> addListOfCategories (
            @Valid @RequestBody CategoryAddBindingModel categoryAddBindingModel) {

        categoryService.addCategories(categoryAddBindingModel);

        return ResponseEntity.ok().build();
    }
}
