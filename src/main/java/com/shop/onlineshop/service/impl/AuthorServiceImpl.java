package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.exception.AuthorNotFoundException;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.repository.AuthorRepository;
import com.shop.onlineshop.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public void initAuthors() {

        if (authorRepository.count() == 0) {

            AuthorEntity rowling = new AuthorEntity("Joanne Rowling");
            authorRepository.save(rowling);

            AuthorEntity tolkien = new AuthorEntity("John Ronald Reuel Tolkien");
            authorRepository.save(tolkien);

            AuthorEntity goodKind = new AuthorEntity("Terry Goodkind");
            authorRepository.save(goodKind);

        }
    }

    //TODO fix the exception later

    @Override
    public AuthorEntity findByName(String name) {
        return authorRepository
                .findByName(name)
                .orElseThrow(IllegalArgumentException::new);
    }
}
