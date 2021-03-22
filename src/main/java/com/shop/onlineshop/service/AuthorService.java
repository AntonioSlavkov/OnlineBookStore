package com.shop.onlineshop.service;

import com.shop.onlineshop.model.entity.AuthorEntity;

public interface AuthorService {
    void initAuthors();

    AuthorEntity findByName(String name);
}
