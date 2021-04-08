package com.shop.onlineshop.web;

import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.repository.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    private long TEST_AUTHOR1_ID, TEST_AUTHOR2_ID;
    private String TEST_AUTHOR_NAME = "Antonio", TEST_AUTHOR2_NAME = "Antonia";

    @BeforeEach
    public void setUp() {
        authorRepository.deleteAll();

        AuthorEntity author1 = new AuthorEntity();
        author1.setAuthor(TEST_AUTHOR_NAME);
        authorRepository.save(author1);
        TEST_AUTHOR1_ID = author1.getId();

        AuthorEntity author2 = new AuthorEntity();
        author2.setAuthor(TEST_AUTHOR2_NAME);
        authorRepository.save(author2);
        TEST_AUTHOR2_ID = author2.getId();
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
        this.mockMvc.perform(get("/authors/222")).andExpect(status().isNotFound());
    }

    @Test
    public void testAuthorFound () throws Exception {
        this.mockMvc
                .perform(get("/authors/author/{id}", TEST_AUTHOR1_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author", is(TEST_AUTHOR_NAME)));
    }
}
