package com.shop.onlineshop.service;

import com.shop.onlineshop.exception.BookNotFoundException;
import com.shop.onlineshop.mapper.BookAddMapper;
import com.shop.onlineshop.mapper.BookUpdateMapper;
import com.shop.onlineshop.mapper.BookViewMapper;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.entity.PictureEntity;
import com.shop.onlineshop.model.view.AuthorViewModel;
import com.shop.onlineshop.model.view.BookViewModel;
import com.shop.onlineshop.repository.BookRepository;
import com.shop.onlineshop.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @InjectMocks
    BookServiceImpl bookServiceTest;

    @Mock
    AuthorService authorService;
    @Mock
    CategoryService categoryService;
    @Mock
    BookRepository bookRepository;
    @Mock
    BookViewMapper bookViewMapper;
    @Mock
    BookAddMapper bookAddMapper;
    @Mock
    BookUpdateMapper bookUpdateMapper;

    BookEntity book1, book2;
    AuthorEntity author1, author2;
    CategoryEntity category1, category2;
    PictureEntity picture1, picture2;
    long book1Id = 1, book2Id = 2, nonExistingId = 420, newBookId = 3;

    @BeforeEach
    public void setUp () {

        bookServiceTest = new BookServiceImpl(this.authorService, this.categoryService,
                this.bookRepository, this.bookViewMapper, this.bookAddMapper, this.bookUpdateMapper);


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
    }

    @Test
    public void testGetAllBooks () {


        List<BookEntity> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        given(bookRepository.findAll()).willReturn(books);

        List<BookViewModel> expected = bookServiceTest.getAllBooks();

        assertEquals(expected, bookViewMapper.bookEntityToBookViewModelList(books));
        verify(bookRepository).findAll();
    }

    @Test
    public void testGetBookById () {


        when(bookRepository.findById(book2.getId())).thenReturn(Optional.of(book2));
        BookEntity book = new BookEntity();
        book.setId(book2Id);

        BookViewModel expected = bookServiceTest
                .getBookById(book.getId());

        assertThat(expected).isSameAs(bookViewMapper.bookEntityToBookViewModel(book));
        verify(bookRepository).findById(book.getId());
    }

    @Test
    public void testGetBookByIdShouldThrowBookNotFound () {

        when(bookRepository.findById(any())).thenThrow(BookNotFoundException.class);

        assertThrows(BookNotFoundException.class, () -> {
            bookServiceTest.getBookById(nonExistingId);
        });
    }
    //TODO This is not working
    @Test
    public void testDeleteBookById () {

        when(bookRepository.findById(book1Id)).thenReturn(Optional.of(book1));

        bookServiceTest.deleteBookById(book1.getId());
        verify(bookRepository).deleteById(book1.getId());
    }

    //TODO This is not working
    @Test
    public void testDeleteBookShouldThrowBookNotFound () {

        when(bookRepository.findById(any())).thenThrow(BookNotFoundException.class);

        assertThrows(BookNotFoundException.class, () -> {
            bookServiceTest.deleteBookById(nonExistingId);
        });
    }

}
