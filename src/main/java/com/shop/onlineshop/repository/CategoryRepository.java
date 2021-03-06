package com.shop.onlineshop.repository;

import com.shop.onlineshop.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByCategory(String name);

    boolean existsCategoryEntityByCategory(String name);


}
