package com.shop.onlineshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.onlineshop.exception.BookAlreadyExistException;
import com.shop.onlineshop.exception.BookNotFoundException;
import com.shop.onlineshop.mapper.AuthorAddMapperImpl;
import com.shop.onlineshop.mapper.AuthorViewMapperImpl;
import com.shop.onlineshop.mapper.BookAddMapper;
import com.shop.onlineshop.mapper.BookViewMapper;
import com.shop.onlineshop.mapper.BookViewMapperImpl;
import com.shop.onlineshop.mapper.CategoryAddMapperImpl;
import com.shop.onlineshop.mapper.CategoryViewMapperImpl;
import com.shop.onlineshop.model.binding.BookAddBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.entity.PictureEntity;
import com.shop.onlineshop.model.view.BookViewModel;
import com.shop.onlineshop.repository.AuthorRepository;
import com.shop.onlineshop.repository.BookRepository;
import com.shop.onlineshop.repository.CategoryRepository;
import com.shop.onlineshop.service.AuthorService;
import com.shop.onlineshop.service.CategoryService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BookServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class BookServiceImplTest {
    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookAddMapper bookAddMapper;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookServiceImpl bookServiceImpl;


    @MockBean
    private BookViewMapper bookViewMapper;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testGetAllBooks() {
        ArrayList<BookViewModel> bookViewModelList = new ArrayList<BookViewModel>();
        when(this.bookViewMapper.bookEntityToBookViewModelList((List<BookEntity>) any())).thenReturn(bookViewModelList);
        when(this.bookRepository.findAll()).thenReturn(new ArrayList<BookEntity>());
        List<BookViewModel> actualAllBooks = this.bookServiceImpl.getAllBooks();
        assertSame(bookViewModelList, actualAllBooks);
        assertTrue(actualAllBooks.isEmpty());
        verify(this.bookRepository).findAll();
        verify(this.bookViewMapper).bookEntityToBookViewModelList((List<BookEntity>) any());
    }

    @Test
    public void testGetBookById() {
        BookViewModel bookViewModel = new BookViewModel();
        when(this.bookViewMapper.bookEntityToBookViewModel((BookEntity) any())).thenReturn(bookViewModel);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(123L);
        categoryEntity.setCategory("Category");

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(123L);
        authorEntity.setAuthor("JaneDoe");

        BookEntity bookEntity = new BookEntity();
        bookEntity.setLanguage("Language");
        bookEntity.setPictureUrls(new ArrayList<PictureEntity>());
        bookEntity.setMainCategory(categoryEntity);
        bookEntity.setSubCategories(new HashSet<CategoryEntity>());
        bookEntity.setId(123L);
        bookEntity.setPages(1);
        bookEntity.setPrice(BigDecimal.valueOf(42L));
        bookEntity.setTitle("Dr");
        bookEntity.setDescription("The characteristics of someone or something");
        bookEntity.setAuthor(authorEntity);
        Optional<BookEntity> ofResult = Optional.<BookEntity>of(bookEntity);
        when(this.bookRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(bookViewModel, this.bookServiceImpl.getBookById(123L));
        verify(this.bookRepository).findById((Long) any());
        verify(this.bookViewMapper).bookEntityToBookViewModel((BookEntity) any());
    }

    @Test
    public void testGetBookById2() {
        when(this.bookViewMapper.bookEntityToBookViewModel((BookEntity) any())).thenReturn(new BookViewModel());
        when(this.bookRepository.findById((Long) any())).thenReturn(Optional.<BookEntity>empty());
        assertThrows(BookNotFoundException.class, () -> this.bookServiceImpl.getBookById(123L));
        verify(this.bookRepository).findById((Long) any());
    }

    @Test
    public void testAddBook() {
        BookAddMapper bookAddMapper = mock(BookAddMapper.class);
        when(bookAddMapper.BookAddBindingToBookEntity((BookAddBindingModel) any()))
                .thenThrow(new BookNotFoundException("An error occurred", HttpStatus.CONTINUE));
        AuthorRepository authorRepository = mock(AuthorRepository.class);
        AuthorViewMapperImpl authorViewMapper = new AuthorViewMapperImpl();
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository, authorViewMapper,
                new AuthorAddMapperImpl());
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        CategoryViewMapperImpl categoryViewMapper = new CategoryViewMapperImpl();
        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository, categoryViewMapper,
                new CategoryAddMapperImpl());
        BookRepository bookRepository = mock(BookRepository.class);
        BookViewMapperImpl bookViewMapper = new BookViewMapperImpl();
        BookServiceImpl bookServiceImpl = new BookServiceImpl(authorService, categoryService, bookRepository,
                bookViewMapper, bookAddMapper);
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.addBook(new BookAddBindingModel()));
        verify(bookAddMapper).BookAddBindingToBookEntity((BookAddBindingModel) any());
    }

//    @Test
//    public void testAddBook2() {
//        AuthorRepository authorRepository = mock(AuthorRepository.class);
//        when(authorRepository.findByAuthor(anyString()))
//                .thenThrow(new BookNotFoundException("An error occurred", HttpStatus.CONTINUE));
//        when(authorRepository.existsAuthorEntityByAuthor(anyString())).thenReturn(true);
//        AuthorViewMapperImpl authorViewMapper = new AuthorViewMapperImpl();
//        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository, authorViewMapper,
//                new AuthorAddMapperImpl());
//
//        CategoryEntity categoryEntity = new CategoryEntity();
//        categoryEntity.setId(123L);
//        categoryEntity.setCategory("Category");
//
//        AuthorEntity authorEntity = new AuthorEntity();
//        authorEntity.setId(123L);
//        authorEntity.setAuthor("JaneDoe");
//
//        BookEntity bookEntity = new BookEntity();
//        bookEntity.setLanguage("Language");
//        bookEntity.setPictureUrls(new ArrayList<PictureEntity>());
//        bookEntity.setMainCategory(categoryEntity);
//        bookEntity.setSubCategories(new HashSet<CategoryEntity>());
//        bookEntity.setId(123L);
//        bookEntity.setPages(1);
//        bookEntity.setPrice(BigDecimal.valueOf(42L));
//        bookEntity.setTitle("Dr");
//        bookEntity.setDescription("The characteristics of someone or something");
//        bookEntity.setAuthor(authorEntity);
//        BookAddMapper bookAddMapper = mock(BookAddMapper.class);
//        when(bookAddMapper.BookAddBindingToBookEntity((BookAddBindingModel) any())).thenReturn(bookEntity);
//        CategoryRepository categoryRepository = mock(CategoryRepository.class);
//        CategoryViewMapperImpl categoryViewMapper = new CategoryViewMapperImpl();
//        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository, categoryViewMapper,
//                new CategoryAddMapperImpl());
//        BookRepository bookRepository = mock(BookRepository.class);
//        BookViewMapperImpl bookViewMapper = new BookViewMapperImpl();
//        BookServiceImpl bookServiceImpl = new BookServiceImpl(authorService, categoryService, bookRepository,
//                bookViewMapper, bookAddMapper);
//
//        AuthorEntity authorEntity1 = new AuthorEntity();
//        authorEntity1.setId(123L);
//        authorEntity1.setAuthor("JaneDoe");
//
//        BookAddBindingModel bookAddBindingModel = new BookAddBindingModel();
//        bookAddBindingModel.setAuthor(authorEntity1);
//        bookServiceImpl.addBook(bookAddBindingModel);
//        verify(authorRepository).findByAuthor(anyString());
//        verify(authorRepository).existsAuthorEntityByAuthor(anyString());
//        verify(bookAddMapper).BookAddBindingToBookEntity((BookAddBindingModel) any());
//    }
//
//    @Test
//    public void testAddBook3() {
//        AuthorEntity authorEntity = new AuthorEntity();
//        authorEntity.setId(123L);
//        authorEntity.setAuthor("JaneDoe");
//        AuthorRepository authorRepository = mock(AuthorRepository.class);
//        when(authorRepository.findByAuthor(anyString())).thenReturn(Optional.<AuthorEntity>of(authorEntity));
//        when(authorRepository.existsAuthorEntityByAuthor(anyString())).thenReturn(true);
//        AuthorViewMapperImpl authorViewMapper = new AuthorViewMapperImpl();
//        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository, authorViewMapper,
//                new AuthorAddMapperImpl());
//        CategoryRepository categoryRepository = mock(CategoryRepository.class);
//        when(categoryRepository.findByCategory(anyString()))
//                .thenThrow(new BookNotFoundException("An error occurred", HttpStatus.CONTINUE));
//        when(categoryRepository.existsCategoryEntityByCategory(anyString())).thenReturn(true);
//        CategoryViewMapperImpl categoryViewMapper = new CategoryViewMapperImpl();
//        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository, categoryViewMapper,
//                new CategoryAddMapperImpl());
//
//        CategoryEntity categoryEntity = new CategoryEntity();
//        categoryEntity.setId(123L);
//        categoryEntity.setCategory("Category");
//
//        AuthorEntity authorEntity1 = new AuthorEntity();
//        authorEntity1.setId(123L);
//        authorEntity1.setAuthor("JaneDoe");
//
//        BookEntity bookEntity = new BookEntity();
//        bookEntity.setLanguage("Language");
//        bookEntity.setPictureUrls(new ArrayList<PictureEntity>());
//        bookEntity.setMainCategory(categoryEntity);
//        bookEntity.setSubCategories(new HashSet<CategoryEntity>());
//        bookEntity.setId(123L);
//        bookEntity.setPages(1);
//        bookEntity.setPrice(BigDecimal.valueOf(42L));
//        bookEntity.setTitle("Dr");
//        bookEntity.setDescription("The characteristics of someone or something");
//        bookEntity.setAuthor(authorEntity1);
//        BookAddMapper bookAddMapper = mock(BookAddMapper.class);
//        when(bookAddMapper.BookAddBindingToBookEntity((BookAddBindingModel) any())).thenReturn(bookEntity);
//        BookRepository bookRepository = mock(BookRepository.class);
//        BookViewMapperImpl bookViewMapper = new BookViewMapperImpl();
//        BookServiceImpl bookServiceImpl = new BookServiceImpl(authorService, categoryService, bookRepository,
//                bookViewMapper, bookAddMapper);
//
//        AuthorEntity authorEntity2 = new AuthorEntity();
//        authorEntity2.setId(123L);
//        authorEntity2.setAuthor("JaneDoe");
//
//        CategoryEntity categoryEntity1 = new CategoryEntity();
//        categoryEntity1.setId(123L);
//        categoryEntity1.setCategory("Category");
//
//        BookAddBindingModel bookAddBindingModel = new BookAddBindingModel();
//        bookAddBindingModel.setMainCategory(categoryEntity1);
//        bookAddBindingModel.setAuthor(authorEntity2);
//        bookServiceImpl.addBook(bookAddBindingModel);
//        verify(authorRepository).findByAuthor(anyString());
//        verify(authorRepository).existsAuthorEntityByAuthor(anyString());
//        verify(bookAddMapper).BookAddBindingToBookEntity((BookAddBindingModel) any());
//        verify(categoryRepository).findByCategory(anyString());
//        verify(categoryRepository).existsCategoryEntityByCategory(anyString());
//    }

    @Test
    public void testAddBook4() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(123L);
        authorEntity.setAuthor("JaneDoe");
        AuthorRepository authorRepository = mock(AuthorRepository.class);
        when(authorRepository.findByAuthor(anyString())).thenReturn(Optional.<AuthorEntity>of(authorEntity));
        when(authorRepository.existsAuthorEntityByAuthor(anyString())).thenReturn(true);
        AuthorViewMapperImpl authorViewMapper = new AuthorViewMapperImpl();
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository, authorViewMapper,
                new AuthorAddMapperImpl());

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(123L);
        categoryEntity.setCategory("Category");
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        when(categoryRepository.findByCategory(anyString())).thenReturn(Optional.<CategoryEntity>of(categoryEntity));
        when(categoryRepository.existsCategoryEntityByCategory(anyString())).thenReturn(true);
        CategoryViewMapperImpl categoryViewMapper = new CategoryViewMapperImpl();
        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository, categoryViewMapper,
                new CategoryAddMapperImpl());

        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setId(123L);
        categoryEntity1.setCategory("Category");

        AuthorEntity authorEntity1 = new AuthorEntity();
        authorEntity1.setId(123L);
        authorEntity1.setAuthor("JaneDoe");

        BookEntity bookEntity = new BookEntity();
        bookEntity.setLanguage("Language");
        bookEntity.setPictureUrls(new ArrayList<PictureEntity>());
        bookEntity.setMainCategory(categoryEntity1);
        bookEntity.setSubCategories(new HashSet<CategoryEntity>());
        bookEntity.setId(123L);
        bookEntity.setPages(1);
        bookEntity.setPrice(BigDecimal.valueOf(42L));
        bookEntity.setTitle("Dr");
        bookEntity.setDescription("The characteristics of someone or something");
        bookEntity.setAuthor(authorEntity1);
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.<BookEntity>of(bookEntity));

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setId(123L);
        categoryEntity2.setCategory("Category");

        AuthorEntity authorEntity2 = new AuthorEntity();
        authorEntity2.setId(123L);
        authorEntity2.setAuthor("JaneDoe");

        BookEntity bookEntity1 = new BookEntity();
        bookEntity1.setLanguage("Language");
        bookEntity1.setPictureUrls(new ArrayList<PictureEntity>());
        bookEntity1.setMainCategory(categoryEntity2);
        bookEntity1.setSubCategories(new HashSet<CategoryEntity>());
        bookEntity1.setId(123L);
        bookEntity1.setPages(1);
        bookEntity1.setPrice(BigDecimal.valueOf(42L));
        bookEntity1.setTitle("Dr");
        bookEntity1.setDescription("The characteristics of someone or something");
        bookEntity1.setAuthor(authorEntity2);
        BookAddMapper bookAddMapper = mock(BookAddMapper.class);
        when(bookAddMapper.BookAddBindingToBookEntity((BookAddBindingModel) any())).thenReturn(bookEntity1);
        BookViewMapperImpl bookViewMapper = new BookViewMapperImpl();
        BookServiceImpl bookServiceImpl = new BookServiceImpl(authorService, categoryService, bookRepository,
                bookViewMapper, bookAddMapper);

        AuthorEntity authorEntity3 = new AuthorEntity();
        authorEntity3.setId(123L);
        authorEntity3.setAuthor("JaneDoe");

        CategoryEntity categoryEntity3 = new CategoryEntity();
        categoryEntity3.setId(123L);
        categoryEntity3.setCategory("Category");

        BookAddBindingModel bookAddBindingModel = new BookAddBindingModel();
        bookAddBindingModel.setSubCategories(new HashSet<CategoryEntity>());
        bookAddBindingModel.setMainCategory(categoryEntity3);
        bookAddBindingModel.setAuthor(authorEntity3);
        assertThrows(BookAlreadyExistException.class, () -> bookServiceImpl.addBook(bookAddBindingModel));
        verify(authorRepository).findByAuthor(anyString());
        verify(authorRepository).existsAuthorEntityByAuthor(anyString());
        verify(bookAddMapper).BookAddBindingToBookEntity((BookAddBindingModel) any());
        verify(bookRepository).findByTitle(anyString());
        verify(categoryRepository).findByCategory(anyString());
        verify(categoryRepository).existsCategoryEntityByCategory(anyString());
    }

    @Test
    public void testAddBook5() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(123L);
        authorEntity.setAuthor("JaneDoe");
        Optional<AuthorEntity> ofResult = Optional.<AuthorEntity>of(authorEntity);

        AuthorEntity authorEntity1 = new AuthorEntity();
        authorEntity1.setId(123L);
        authorEntity1.setAuthor("JaneDoe");
        AuthorRepository authorRepository = mock(AuthorRepository.class);
        when(authorRepository.save((AuthorEntity) any())).thenReturn(authorEntity1);
        when(authorRepository.findByAuthor(anyString())).thenReturn(ofResult);
        when(authorRepository.existsAuthorEntityByAuthor(anyString())).thenReturn(false);
        AuthorViewMapperImpl authorViewMapper = new AuthorViewMapperImpl();
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository, authorViewMapper,
                new AuthorAddMapperImpl());

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(123L);
        categoryEntity.setCategory("Category");
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        when(categoryRepository.findByCategory(anyString())).thenReturn(Optional.<CategoryEntity>of(categoryEntity));
        when(categoryRepository.existsCategoryEntityByCategory(anyString())).thenReturn(true);
        CategoryViewMapperImpl categoryViewMapper = new CategoryViewMapperImpl();
        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository, categoryViewMapper,
                new CategoryAddMapperImpl());
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.findByTitle(anyString()))
                .thenThrow(new BookNotFoundException("An error occurred", HttpStatus.CONTINUE));

        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setId(123L);
        categoryEntity1.setCategory("Category");

        AuthorEntity authorEntity2 = new AuthorEntity();
        authorEntity2.setId(123L);
        authorEntity2.setAuthor("JaneDoe");

        BookEntity bookEntity = new BookEntity();
        bookEntity.setLanguage("Language");
        bookEntity.setPictureUrls(new ArrayList<PictureEntity>());
        bookEntity.setMainCategory(categoryEntity1);
        bookEntity.setSubCategories(new HashSet<CategoryEntity>());
        bookEntity.setId(123L);
        bookEntity.setPages(1);
        bookEntity.setPrice(BigDecimal.valueOf(42L));
        bookEntity.setTitle("Dr");
        bookEntity.setDescription("The characteristics of someone or something");
        bookEntity.setAuthor(authorEntity2);
        BookAddMapper bookAddMapper = mock(BookAddMapper.class);
        when(bookAddMapper.BookAddBindingToBookEntity((BookAddBindingModel) any())).thenReturn(bookEntity);
        BookViewMapperImpl bookViewMapper = new BookViewMapperImpl();
        BookServiceImpl bookServiceImpl = new BookServiceImpl(authorService, categoryService, bookRepository,
                bookViewMapper, bookAddMapper);

        AuthorEntity authorEntity3 = new AuthorEntity();
        authorEntity3.setId(123L);
        authorEntity3.setAuthor("JaneDoe");

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setId(123L);
        categoryEntity2.setCategory("Category");

        BookAddBindingModel bookAddBindingModel = new BookAddBindingModel();
        bookAddBindingModel.setSubCategories(new HashSet<CategoryEntity>());
        bookAddBindingModel.setMainCategory(categoryEntity2);
        bookAddBindingModel.setAuthor(authorEntity3);
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.addBook(bookAddBindingModel));
        verify(authorRepository).save((AuthorEntity) any());
        verify(authorRepository).findByAuthor(anyString());
        verify(authorRepository).existsAuthorEntityByAuthor(anyString());
        verify(bookAddMapper).BookAddBindingToBookEntity((BookAddBindingModel) any());
        verify(bookRepository).findByTitle(anyString());
        verify(categoryRepository).findByCategory(anyString());
        verify(categoryRepository).existsCategoryEntityByCategory(anyString());
    }

    @Test
    public void testAddBook6() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(123L);
        authorEntity.setAuthor("JaneDoe");
        Optional<AuthorEntity> ofResult = Optional.<AuthorEntity>of(authorEntity);

        AuthorEntity authorEntity1 = new AuthorEntity();
        authorEntity1.setId(123L);
        authorEntity1.setAuthor("JaneDoe");
        AuthorRepository authorRepository = mock(AuthorRepository.class);
        when(authorRepository.save((AuthorEntity) any())).thenReturn(authorEntity1);
        when(authorRepository.findByAuthor(anyString())).thenReturn(ofResult);
        when(authorRepository.existsAuthorEntityByAuthor(anyString())).thenReturn(false);
        AuthorViewMapperImpl authorViewMapper = new AuthorViewMapperImpl();
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository, authorViewMapper,
                new AuthorAddMapperImpl());

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(123L);
        categoryEntity.setCategory("Category");
        Optional<CategoryEntity> ofResult1 = Optional.<CategoryEntity>of(categoryEntity);

        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setId(123L);
        categoryEntity1.setCategory("Category");
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        when(categoryRepository.save((CategoryEntity) any())).thenReturn(categoryEntity1);
        when(categoryRepository.findByCategory(anyString())).thenReturn(ofResult1);
        when(categoryRepository.existsCategoryEntityByCategory(anyString())).thenReturn(false);
        CategoryViewMapperImpl categoryViewMapper = new CategoryViewMapperImpl();
        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository, categoryViewMapper,
                new CategoryAddMapperImpl());
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.findByTitle(anyString()))
                .thenThrow(new BookNotFoundException("An error occurred", HttpStatus.CONTINUE));

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setId(123L);
        categoryEntity2.setCategory("Category");

        AuthorEntity authorEntity2 = new AuthorEntity();
        authorEntity2.setId(123L);
        authorEntity2.setAuthor("JaneDoe");

        BookEntity bookEntity = new BookEntity();
        bookEntity.setLanguage("Language");
        bookEntity.setPictureUrls(new ArrayList<PictureEntity>());
        bookEntity.setMainCategory(categoryEntity2);
        bookEntity.setSubCategories(new HashSet<CategoryEntity>());
        bookEntity.setId(123L);
        bookEntity.setPages(1);
        bookEntity.setPrice(BigDecimal.valueOf(42L));
        bookEntity.setTitle("Dr");
        bookEntity.setDescription("The characteristics of someone or something");
        bookEntity.setAuthor(authorEntity2);
        BookAddMapper bookAddMapper = mock(BookAddMapper.class);
        when(bookAddMapper.BookAddBindingToBookEntity((BookAddBindingModel) any())).thenReturn(bookEntity);
        BookViewMapperImpl bookViewMapper = new BookViewMapperImpl();
        BookServiceImpl bookServiceImpl = new BookServiceImpl(authorService, categoryService, bookRepository,
                bookViewMapper, bookAddMapper);

        AuthorEntity authorEntity3 = new AuthorEntity();
        authorEntity3.setId(123L);
        authorEntity3.setAuthor("JaneDoe");

        CategoryEntity categoryEntity3 = new CategoryEntity();
        categoryEntity3.setId(123L);
        categoryEntity3.setCategory("Category");

        BookAddBindingModel bookAddBindingModel = new BookAddBindingModel();
        bookAddBindingModel.setSubCategories(new HashSet<CategoryEntity>());
        bookAddBindingModel.setMainCategory(categoryEntity3);
        bookAddBindingModel.setAuthor(authorEntity3);
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.addBook(bookAddBindingModel));
        verify(authorRepository).save((AuthorEntity) any());
        verify(authorRepository).findByAuthor(anyString());
        verify(authorRepository).existsAuthorEntityByAuthor(anyString());
        verify(bookAddMapper).BookAddBindingToBookEntity((BookAddBindingModel) any());
        verify(bookRepository).findByTitle(anyString());
        verify(categoryRepository).findByCategory(anyString());
        verify(categoryRepository).save((CategoryEntity) any());
        verify(categoryRepository).existsCategoryEntityByCategory(anyString());
    }

    @Test
    public void testDeleteBookById() {
        doNothing().when(this.bookRepository).deleteById((Long) any());
        when(this.bookRepository.existsById((Long) any())).thenReturn(true);
        this.bookServiceImpl.deleteBookById(123L);
        verify(this.bookRepository).deleteById((Long) any());
        verify(this.bookRepository).existsById((Long) any());
    }

    @Test
    public void testDeleteBookById2() {
        doNothing().when(this.bookRepository).deleteById((Long) any());
        when(this.bookRepository.existsById((Long) any())).thenReturn(false);
        assertThrows(BookNotFoundException.class, () -> this.bookServiceImpl.deleteBookById(123L));
        verify(this.bookRepository).existsById((Long) any());
    }

    @Test
    public void testExistsByBookTitle() {
        when(this.bookRepository.existsByTitle(anyString())).thenReturn(true);
        assertTrue(this.bookServiceImpl.existsByBookTitle("Dr"));
        verify(this.bookRepository).existsByTitle(anyString());
    }
}

