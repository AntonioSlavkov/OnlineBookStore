package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.repository.CategoryRepository;
import com.shop.onlineshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void initCategories() {

        if (categoryRepository.count() == 0) {

            CategoryEntity fantasy = new CategoryEntity("Fantasy");
            categoryRepository.save(fantasy);

            CategoryEntity fiction = new CategoryEntity("Fiction");
            categoryRepository.save(fiction);

            CategoryEntity romance = new CategoryEntity("Romance");
            categoryRepository.save(romance);

            CategoryEntity drama = new CategoryEntity("Drama");
            categoryRepository.save(drama);

            CategoryEntity thriller = new CategoryEntity("Thriller");
            categoryRepository.save(thriller);

        }
    }

    @Override
    public CategoryEntity findByName(String name) {
        return categoryRepository
                .findByName(name)
                .orElseThrow(IllegalArgumentException::new);
    }
}
