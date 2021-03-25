package com.shop.onlineshop.service;

import com.shop.onlineshop.model.binding.AuthorAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.view.AuthorViewModel;

import java.util.List;

public interface AuthorService {
//    void initAuthors();

    AuthorEntity findByName(String name);

    AuthorEntity saveAuthor (AuthorEntity name);

    List<AuthorViewModel> getAllAuthors();

    AuthorViewModel getAuthorById(Long id);

    void addAuthors(AuthorAddBindingModel authorAddBindingModel);
}
