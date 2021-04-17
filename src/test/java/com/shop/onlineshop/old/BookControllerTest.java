package com.shop.onlineshop.old;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.onlineshop.model.binding.BookAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.entity.PictureEntity;
import com.shop.onlineshop.repository.AuthorRepository;
import com.shop.onlineshop.repository.BookRepository;
import com.shop.onlineshop.repository.CategoryRepository;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookRepository bookRepository;

    BookEntity book1, book2;
    AuthorEntity author1, author2;
    CategoryEntity category1, category2;
    PictureEntity picture1, picture2;
    long book1Id = 1, book2Id = 2, nonExistingId = 420, newBookId = 3;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();

        author1 = new AuthorEntity();
        author1.setAuthor("Antonio");

        author2 = new AuthorEntity();
        author2.setAuthor("Antonia");


        category1 = new CategoryEntity();
        category1.setCategory("Fantasy");

        category2 = new CategoryEntity();
        category2.setCategory("Romance");

        picture1 = new PictureEntity();
        picture1.setImageUrl("https://images-na.ssl-images-amazon.com/images/I/81t2CVWEsUL.jpg");

        picture2 = new PictureEntity();
        picture2.setImageUrl("https://images-na.ssl-images-amazon.com/images/I/51M708KEH5L.jpg");

        book1 = new BookEntity();
        book1.setId(book1Id);
        book1.setTitle("Antonio Potter and Spring Security");
        book1.setPages(300);
        book1.setPrice(BigDecimal.valueOf(13));
        book1.setLanguage("English");
        book1.setDescription("Mnogo hubav description ima tazi kniga");
        book1.setAuthor(author1);
        book1.setPictureUrls(List.of(picture1, picture2));
        book1.setMainCategory(category1);
        book1.setSubCategories(Set.of(category2));

        book2 = new BookEntity();
        book2.setId(book2Id);
        book2.setTitle("Antonio Potter and The Integration Tests");
        book2.setPages(250);
        book2.setPrice(BigDecimal.valueOf(15));
        book2.setLanguage("English");
        book2.setDescription("Oshte edin qk description za kniga");
        book2.setAuthor(author2);
        book2.setPictureUrls(List.of(picture1, picture2));
        book2.setMainCategory(category2);
        book2.setSubCategories(Set.of(category1));

        when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));
        when(bookRepository.findById(book2.getId())).thenReturn(Optional.of(book2));
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        when(bookRepository.save(any())).thenAnswer(
                (Answer<BookEntity>) invocation -> {
                    BookEntity bookToSave = invocation.getArgument(0);
                    bookToSave.setId(newBookId);
                    return bookToSave;
                }
        );

        doAnswer(invocation -> {
            BookEntity bookToDelete = invocation.getArgument(0);
            return book2Id;
        }).when(bookRepository).deleteById(any());
    }

    @AfterEach
    public void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    public void testGetAllBooksShouldReturnCorrectStatusCode() throws Exception {
        this.mockMvc
                .perform(get("/books/all")).andExpect(status().isOk());
    }

    @Test
    public void testGetBookById() throws Exception {
        this.mockMvc
                .perform(get("/books/book/{id}", book1Id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(book1.getTitle())))
                .andExpect(jsonPath("$.pages", is(book1.getPages())))
                .andExpect(jsonPath("$.price", is(book1.getPrice().intValue())))
                .andExpect(jsonPath("$.language", is(book1.getLanguage())))
                .andExpect(jsonPath("$.description", is(book1.getDescription())))
                .andExpect(jsonPath("$.author.author", is(book1.getAuthor().getAuthor())))
                .andExpect(jsonPath("$.pictureUrls[0].imageUrl", is(book1.getPictureUrls().get(0).getImageUrl())))
                .andExpect(jsonPath("$.pictureUrls[1].imageUrl", is(book1.getPictureUrls().get(1).getImageUrl())))
                .andExpect(jsonPath("$.mainCategory.category", is(book1.getMainCategory().getCategory())))
                .andExpect(jsonPath("$.subCategories[0].category", is(book1
                        .getSubCategories()
                        .stream()
                        .findAny()
                        .get()
                        .getCategory())));
    }

    @Test
    public void testGetBookByIdNotFound() throws Exception {
        this.mockMvc
                .perform(get("/books/book/{id}", nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddBook() throws Exception {

        AuthorEntity author3 = new AuthorEntity();
        author3.setAuthor("Aizen");

        PictureEntity picture1 = new PictureEntity();
        picture1.setImageUrl("https://images-na.ssl-images-amazon.com/images/I/512wolRCzHL.jpg");
        PictureEntity picture2 = new PictureEntity();
        picture2.setImageUrl("https://images-na.ssl-images-amazon.com/images/I/41Cqh4aThYL.jpg");

        CategoryEntity category3 = new CategoryEntity();
        category3.setCategory("Drama");

        CategoryEntity category4 = new CategoryEntity();
        category4.setCategory("Thriller");

        BookAddBindingModel book3 = new BookAddBindingModel();

        book3.setTitle("New Title");
        book3.setPages(300);
        book3.setPrice(BigDecimal.valueOf(20));
        book3.setLanguage("English");
        book3.setDescription("The description for this book is very cool");
        book3.setAuthor(author3);
        book3.setPictureUrls(List.of(picture1, picture2));
        book3.setMainCategory(category3);
        book3.setSubCategories(Set.of(category4));

        String json = this.objectMapper.writeValueAsString(book3);


        this.mockMvc
                .perform(post("/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        ArgumentCaptor<BookEntity> argument = ArgumentCaptor.forClass(BookEntity.class);
        Mockito.verify(bookRepository, times(1)).save(argument.capture());

        BookEntity newBookActual = argument.getValue();

        Assertions.assertEquals(book3.getTitle(), newBookActual.getTitle());
        Assertions.assertEquals(newBookId, newBookActual.getId());

    }


//    @Test
//    public void testDeleteBookById() throws Exception {
//
//
//        doNothing().when(bookRepository).deleteById(book2Id);
//
//        mockMvc.perform(delete("/books/delete/{id}", book2Id)
//                .contentType(MediaType.APPLICATION_JSON) )
//                .andExpect(status().isNoContent());
//
//
//
//    }

    @Test
    public void testDeleteBookByIdNotFound() throws Exception {
        mockMvc.perform(delete("/books/delete/{id}", nonExistingId))
                .andExpect(status().isNotFound());
    }
}
