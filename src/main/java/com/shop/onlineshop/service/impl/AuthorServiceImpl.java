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
                .orElse(null);
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
                                HttpStatus.NOT_FOUND)));
    }

    @Override
    public void addAuthors(AuthorAddBindingModel authorAddBindingModel) {


        for (String authorBinding : authorAddBindingModel.getAuthors()) {


            if (authorRepository.findByAuthor(authorBinding).isPresent()) {
                throw new AuthorAlreadyExistException("Author already exist.", HttpStatus.CONFLICT);
            }

            AuthorEntity author = authorAddMapper.authorAddBindingToAuthorEntity(authorBinding);
            authorRepository.save(author);
        }



    }


}
