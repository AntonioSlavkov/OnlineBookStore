package com.shop.onlineshop.repository;

import com.shop.onlineshop.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    CartEntity findByBookId(Long id);

    CartEntity findAllByUserId(Long id);

    void deleteByUserId(Long id);
}
