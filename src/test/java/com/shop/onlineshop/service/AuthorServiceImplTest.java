package com.shop.onlineshop.service;

import com.shop.onlineshop.mapper.AuthorAddMapper;
import com.shop.onlineshop.mapper.AuthorViewMapper;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.view.AuthorViewModel;
import com.shop.onlineshop.repository.AuthorRepository;
import com.shop.onlineshop.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    AuthorServiceImpl authorServiceTest;
    AuthorEntity author1, author2;
    long AUTHOR1_ID = 1, AUTHOR2_ID = 2;

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
    public void testFindByNameReturnsNull () {
        when(mockAuthorRepository.findByAuthor(author2.getAuthor())).thenReturn(Optional.of(author2));
        AuthorEntity author = new AuthorEntity();
        author.setAuthor(null);

        AuthorEntity authorTest = authorServiceTest
                .findByName(author.getAuthor());

        Assertions.assertEquals(author.getAuthor(), authorTest.getAuthor());
//        Assertions.assertNull(authorTest);

    }

    @Test
    public void testGetAllAuthors () {

        when(mockAuthorRepository.findAll()).thenReturn(List.of(author1, author2));
        List<AuthorViewModel> authors = authorServiceTest.getAllAuthors();

        Assertions.assertEquals(2, authors.size());
    }

}

