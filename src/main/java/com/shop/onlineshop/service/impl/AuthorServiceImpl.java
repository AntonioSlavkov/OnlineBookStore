package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.exception.AuthorAlreadyExistException;
import com.shop.onlineshop.exception.AuthorNotFoundException;
import com.shop.onlineshop.mapper.AuthorAddMapper;
import com.shop.onlineshop.mapper.AuthorViewMapper;
import com.shop.onlineshop.model.binding.AuthorAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.view.AuthorViewModel;
import com.shop.onlineshop.repository.AuthorRepository;
import com.shop.onlineshop.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorViewMapper authorViewMapper;
    private final AuthorAddMapper authorAddMapper;

    @Override
    public AuthorEntity findByName(String name) {

        return authorRepository
                .findByAuthor(name)
                .orElseThrow( () -> new AuthorNotFoundException("Author does not exist", NOT_FOUND));
    }

    @Override
    public AuthorEntity saveAuthor(AuthorEntity name) {
        return authorRepository.save(name);
    }

    @Override
    public List<AuthorViewModel> getAllAuthors() {

        return authorViewMapper
                .authorEntityToAuthorViewList(authorRepository.findAll());
    }

    @Override
    public AuthorViewModel getAuthorById(Long id) {
        return authorViewMapper
                .authorEntityToAuthorView(authorRepository.findById(id)
                        .orElseThrow(() -> new AuthorNotFoundException(
                                "Author does not exist",
                                NOT_FOUND)));
    }

    @Override
    public AuthorEntity addAuthor(AuthorAddBindingModel authorAddBindingModel) {


        if (existsByName(authorAddBindingModel.getAuthor())) {
            throw new AuthorAlreadyExistException("Author already exists", CONFLICT);
        }

        AuthorEntity author = authorAddMapper.authorAddBindingToAuthorEntity(authorAddBindingModel.getAuthor());
        return authorRepository.save(author);



    }

    @Override
    public boolean existsByName(String name) {
        return authorRepository.existsAuthorEntityByAuthor(name);
    }


}
