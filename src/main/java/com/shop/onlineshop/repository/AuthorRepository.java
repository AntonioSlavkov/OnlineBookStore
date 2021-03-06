package com.shop.onlineshop.repository;

import com.shop.onlineshop.model.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findByAuthor (String author);

    boolean existsAuthorEntityByAuthor(String author);
}
