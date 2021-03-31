package com.shop.onlineshop.repository;

import com.shop.onlineshop.model.entity.UserContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactRepository extends JpaRepository<UserContactEntity, Long> {
}
