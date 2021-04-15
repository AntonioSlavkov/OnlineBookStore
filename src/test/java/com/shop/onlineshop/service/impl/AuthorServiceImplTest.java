package com.shop.onlineshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.onlineshop.exception.AuthorAlreadyExistException;
import com.shop.onlineshop.exception.AuthorNotFoundException;
import com.shop.onlineshop.mapper.AuthorAddMapper;
import com.shop.onlineshop.mapper.AuthorViewMapper;
import com.shop.onlineshop.model.binding.AuthorAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.view.AuthorViewModel;
import com.shop.onlineshop.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthorServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class AuthorServiceImplTest {
    @MockBean
    private AuthorAddMapper authorAddMapper;

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorServiceImpl authorServiceImpl;

    @MockBean
    private AuthorViewMapper authorViewMapper;

    @Test
    public void testFindByName() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(123L);
        authorEntity.setAuthor("JaneDoe");
        Optional<AuthorEntity> ofResult = Optional.<AuthorEntity>of(authorEntity);
        when(this.authorRepository.findByAuthor(anyString())).thenReturn(ofResult);
        assertSame(authorEntity, this.authorServiceImpl.findByName("Name"));
        verify(this.authorRepository).findByAuthor(anyString());
    }

    @Test
    public void testFindByName2() {
        when(this.authorRepository.findByAuthor(anyString())).thenReturn(Optional.<AuthorEntity>empty());
        assertThrows(AuthorNotFoundException.class, () -> this.authorServiceImpl.findByName("Name"));
        verify(this.authorRepository).findByAuthor(anyString());
    }

    @Test
    public void testSaveAuthor() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(123L);
        authorEntity.setAuthor("JaneDoe");
        when(this.authorRepository.save((AuthorEntity) any())).thenReturn(authorEntity);
        assertSame(authorEntity, this.authorServiceImpl.saveAuthor(new AuthorEntity("JaneDoe")));
        verify(this.authorRepository).save((AuthorEntity) any());
    }

    @Test
    public void testGetAllAuthors() {
        ArrayList<AuthorViewModel> authorViewModelList = new ArrayList<AuthorViewModel>();
        when(this.authorViewMapper.authorEntityToAuthorViewList((List<AuthorEntity>) any()))
                .thenReturn(authorViewModelList);
        when(this.authorRepository.findAll()).thenReturn(new ArrayList<AuthorEntity>());
        List<AuthorViewModel> actualAllAuthors = this.authorServiceImpl.getAllAuthors();
        assertSame(authorViewModelList, actualAllAuthors);
        assertTrue(actualAllAuthors.isEmpty());
        verify(this.authorRepository).findAll();
        verify(this.authorViewMapper).authorEntityToAuthorViewList((List<AuthorEntity>) any());
    }

    @Test
    public void testGetAuthorById() {
        AuthorViewModel authorViewModel = new AuthorViewModel();
        when(this.authorViewMapper.authorEntityToAuthorView((AuthorEntity) any())).thenReturn(authorViewModel);

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(123L);
        authorEntity.setAuthor("JaneDoe");
        Optional<AuthorEntity> ofResult = Optional.<AuthorEntity>of(authorEntity);
        when(this.authorRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(authorViewModel, this.authorServiceImpl.getAuthorById(123L));
        verify(this.authorRepository).findById((Long) any());
        verify(this.authorViewMapper).authorEntityToAuthorView((AuthorEntity) any());
    }

    @Test
    public void testGetAuthorById2() {
        when(this.authorViewMapper.authorEntityToAuthorView((AuthorEntity) any())).thenReturn(new AuthorViewModel());
        when(this.authorRepository.findById((Long) any())).thenReturn(Optional.<AuthorEntity>empty());
        assertThrows(AuthorNotFoundException.class, () -> this.authorServiceImpl.getAuthorById(123L));
        verify(this.authorRepository).findById((Long) any());
    }

    @Test
    public void testAddAuthor() {
        when(this.authorRepository.existsAuthorEntityByAuthor(anyString())).thenReturn(true);
        assertThrows(AuthorAlreadyExistException.class,
                () -> this.authorServiceImpl.addAuthor(new AuthorAddBindingModel()));
        verify(this.authorRepository).existsAuthorEntityByAuthor(anyString());
    }

    @Test
    public void testAddAuthor2() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(123L);
        authorEntity.setAuthor("JaneDoe");
        when(this.authorRepository.save((AuthorEntity) any())).thenReturn(authorEntity);
        when(this.authorRepository.existsAuthorEntityByAuthor(anyString())).thenReturn(false);

        AuthorEntity authorEntity1 = new AuthorEntity();
        authorEntity1.setId(123L);
        authorEntity1.setAuthor("JaneDoe");
        when(this.authorAddMapper.authorAddBindingToAuthorEntity(anyString())).thenReturn(authorEntity1);
        assertSame(authorEntity, this.authorServiceImpl.addAuthor(new AuthorAddBindingModel()));
        verify(this.authorAddMapper).authorAddBindingToAuthorEntity(anyString());
        verify(this.authorRepository).save((AuthorEntity) any());
        verify(this.authorRepository).existsAuthorEntityByAuthor(anyString());
    }

    @Test
    public void testExistsByName() {
        when(this.authorRepository.existsAuthorEntityByAuthor(anyString())).thenReturn(true);
        assertTrue(this.authorServiceImpl.existsByName("Name"));
        verify(this.authorRepository).existsAuthorEntityByAuthor(anyString());
    }
}

