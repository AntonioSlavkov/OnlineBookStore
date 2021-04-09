package com.shop.onlineshop.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.AuthorAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.repository.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorRepository authorRepository;

     long TEST_AUTHOR1_ID = 1, TEST_AUTHOR2_ID = 2;
     String TEST_AUTHOR1_NAME = "Antonio", TEST_AUTHOR2_NAME = "Antonia";
     int NON_EXISTING_AUTHOR = 66666;
     AuthorEntity author1, author2;
     int NEW_AUTHOR_ID = 3;

    @BeforeEach
    public void setUp() {


        author1 = new AuthorEntity();
        author1.setAuthor(TEST_AUTHOR1_NAME);
        author1.setId(TEST_AUTHOR1_ID);

        author2 = new AuthorEntity();
        author2.setAuthor(TEST_AUTHOR2_NAME);
        author2.setId(TEST_AUTHOR2_ID);



        when(authorRepository.findById(author1.getId())).thenReturn(Optional.of(author1));
        when(authorRepository.findById(author2.getId())).thenReturn(Optional.of(author2));
        when(authorRepository.findAll()).thenReturn(List.of(author1, author2));

        when(authorRepository.save(any())).thenAnswer(
                (Answer<AuthorEntity>) invocation -> {
                    AuthorEntity authorToSave = invocation.getArgument(0);
                    authorToSave.setId(NEW_AUTHOR_ID);
                    return authorToSave;
                }
        );

    }

    @AfterEach
    public void tearDown () {
        authorRepository.deleteAll();
    }

    @Test
    public void testAuthorsReturnCorrectStatusCode () throws Exception {
        this.mockMvc.perform(get("/authors/all")).andExpect(status().isOk());
    }

    @Test
    public void testAuthorNotFound() throws Exception {
        this.mockMvc
                .perform(get("/authors/author/{id}", NON_EXISTING_AUTHOR))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAuthorFound () throws Exception {
        this.mockMvc
                .perform(get("/authors/author/{id}", author1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author", is(author1.getAuthor())));
    }

    //TODO make the test to work for multiple authors
    @Test
    public void testAddAuthor () throws Exception {

        AuthorAddBindingModel authorToAdd = new AuthorAddBindingModel();
        authorToAdd.setAuthor("Aizen");

        String json = this.objectMapper.writeValueAsString(authorToAdd);

        this.mockMvc
                .perform(post("/authors/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<AuthorEntity> argument = ArgumentCaptor.forClass(AuthorEntity.class);
        Mockito.verify(authorRepository, times(1)).save(argument.capture());

        AuthorEntity newAuthorActual = argument.getValue();

        Assertions.assertEquals(authorToAdd.getAuthor(), newAuthorActual.getAuthor());
        Assertions.assertEquals(NEW_AUTHOR_ID, newAuthorActual.getId());
    }
}
