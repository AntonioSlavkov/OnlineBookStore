package com.shop.onlineshop.old;

import com.shop.onlineshop.exception.AuthorNotFoundException;
import com.shop.onlineshop.mapper.AuthorAddMapper;
import com.shop.onlineshop.mapper.AuthorViewMapper;
import com.shop.onlineshop.model.binding.AuthorAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.view.AuthorViewModel;
import com.shop.onlineshop.repository.AuthorRepository;
import com.shop.onlineshop.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @InjectMocks
    AuthorServiceImpl authorServiceTest;

    AuthorEntity author1, author2;
    long AUTHOR1_ID = 1, AUTHOR2_ID = 2, NEW_AUTHOR_ID = 3;

    @Mock
    AuthorRepository mockAuthorRepository;

    @Mock
    AuthorViewMapper authorViewMapper;
    @Mock
    AuthorAddMapper authorAddMapper;

    @BeforeEach
    public void setUp() {

        author1 = new AuthorEntity();
        author1.setAuthor("Aizen");
        author1.setId(AUTHOR1_ID);

        author2 = new AuthorEntity();
        author2.setAuthor("Antonio");
        author2.setId(AUTHOR2_ID);

        authorServiceTest = new AuthorServiceImpl(
                this.mockAuthorRepository, this.authorViewMapper, this.authorAddMapper);

    }


    @Test
    public void testFindByName() {

        when(mockAuthorRepository.findByAuthor(author2.getAuthor())).thenReturn(Optional.of(author2));
        AuthorEntity author = new AuthorEntity();
        author.setAuthor("Antonio");

        AuthorEntity authorTest = authorServiceTest
                .findByName(author.getAuthor());

        Assertions.assertEquals(author.getAuthor(), authorTest.getAuthor());
    }

    @Test
    public void testFindByNameThrowsAuthorNotFound() {

        when(mockAuthorRepository.findByAuthor(any())).thenThrow(AuthorNotFoundException.class);

        assertThrows(AuthorNotFoundException.class, () -> {
            authorServiceTest.findByName("Not Found");

        });
    }

    @Test
    public void testGetAllAuthors() {

        List<AuthorEntity> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);

        given(mockAuthorRepository.findAll()).willReturn(authors);

        List<AuthorViewModel> expected = authorServiceTest.getAllAuthors();

        assertEquals(expected, authorViewMapper.authorEntityToAuthorViewList(authors));
        verify(mockAuthorRepository).findAll();
    }


//    @Test
//    public void testAddAuthor () {
//
//        AuthorAddBindingModel authorToAdd = new AuthorAddBindingModel();
//        authorToAdd.setAuthor("Aizen");
//
//        when(mockAuthorRepository.save(ArgumentMatchers.any(AuthorEntity.class)))
//                .thenReturn(authorAddMapper
//                        .authorAddBindingToAuthorEntity(authorToAdd.getAuthor()));
//
//        AuthorEntity created = authorServiceTest.addAuthor(authorToAdd);
//        assertThat(created.getAuthor()).isSameAs(authorToAdd.getAuthor());
//        verify(mockAuthorRepository).save(authorAddMapper.authorAddBindingToAuthorEntity(authorToAdd.getAuthor()));
//
//
//
//        ArgumentCaptor<AuthorEntity> argument = ArgumentCaptor.forClass(AuthorEntity.class);
//        Mockito.verify(mockAuthorRepository, times(1)).save(argument.capture());
//
//        AuthorEntity newActualAuthor = argument.getValue();
//
//        Assertions.assertEquals(authorToAdd.getAuthor(), newActualAuthor.getAuthor());
//        Assertions.assertEquals(NEW_AUTHOR_ID, newActualAuthor.getId());
//    }

}

