package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.exception.AuthorNotFoundException;
import com.shop.onlineshop.exception.BookAlreadyExistException;
import com.shop.onlineshop.exception.BookNotFoundException;
import com.shop.onlineshop.exception.CategoryNotFountException;
import com.shop.onlineshop.mapper.BookAddMapper;
import com.shop.onlineshop.mapper.BookUpdateMapper;
import com.shop.onlineshop.mapper.BookViewMapper;
import com.shop.onlineshop.model.binding.BookAddBindingModel;
import com.shop.onlineshop.model.binding.BookUpdateBindingModel;
import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.entity.PictureEntity;
import com.shop.onlineshop.model.view.BookViewModel;
import com.shop.onlineshop.repository.BookRepository;
import com.shop.onlineshop.service.AuthorService;
import com.shop.onlineshop.service.BookService;
import com.shop.onlineshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookRepository bookRepository;
    private final BookViewMapper bookViewMapper;
    private final BookAddMapper bookAddMapper;
    private final BookUpdateMapper bookUpdateMapper;

    @Override
    public List<BookViewModel> getAllBooks() {

        return bookViewMapper.bookEntityToBookViewModelList(bookRepository.findAll());
    }

    @Override
    public BookViewModel getBookById(long id) {

        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("This book does not exists", HttpStatus.NOT_FOUND));

        return bookViewMapper.bookEntityToBookViewModel(book);

    }

    @Override
    @Transactional
    public void addBook(BookAddBindingModel bookAddBindingModel) {

        BookEntity book = bookAddMapper.BookAddBindingToBookEntity(bookAddBindingModel);

        /**
         * Sets the author to the entity and adds the author if he does not exist in the database
         */
        String author = bookAddBindingModel.getAuthor().getAuthor();

        if (!authorService.existsByName(author)) {
            authorService.saveAuthor(bookAddBindingModel.getAuthor());
        }
        book.setAuthor(authorService.findByName(author));


        /**
         * Sets the main category of the book and throws an exception if the category is invalid
         */
        String mainCategory = bookAddBindingModel.getMainCategory().getCategory();

        if (!categoryService.existByCategory(mainCategory)) {
            categoryService.saveCategory(bookAddBindingModel.getMainCategory());
        }
                book.setMainCategory(categoryService.findByName(mainCategory));

        /**
         * Sets the sub categories of the entity and throws an exception if some of the categories are invalid.
         */
        Set<CategoryEntity> subCategories = new HashSet<>();
        List<CategoryEntity> bindingSubCategories = new ArrayList<>(bookAddBindingModel.getSubCategories());

        if (!bindingSubCategories.isEmpty()) {

            for (CategoryEntity bindingSubCategory : bindingSubCategories) {

                if (!categoryService.existByCategory(bindingSubCategory.getCategory())) {
                    categoryService.saveCategory(bindingSubCategory);
                }
                    subCategories.add(categoryService.findByName(bindingSubCategory.getCategory()));
            }
        }
        book.setSubCategories(subCategories);


        if (bookRepository.findByTitle(book.getTitle()).isPresent()) {
            throw new BookAlreadyExistException("This book already exist.", HttpStatus.CONFLICT);
        }

        bookRepository.save(book);

    }

    @Override
    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book does not exist.", HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }

//    @Override
//    public void updateBook(Long id, BookUpdateBindingModel bookUpdateBindingModel) {
//        //TODO implement or delete the update method
//        BookEntity oldBook = bookRepository
//                .findById(id).orElseThrow( () -> new BookNotFoundException(
//                        "Book does not exist.",
//                        HttpStatus.NOT_FOUND));
//
//        BookEntity bookWithNewData = bookUpdateMapper.bookUpdateBindingToBookEntity(bookUpdateBindingModel);
//
//        oldBook.setAuthor(bookWithNewData.getAuthor());
//        oldBook.setMainCategory(bookWithNewData.getMainCategory());
//        oldBook.setSubCategories(bookWithNewData.getSubCategories());
//        oldBook.setPictureUrls(bookWithNewData.getPictureUrls());
//        oldBook.setDescription(bookWithNewData.getDescription());
//        oldBook.setLanguage(bookWithNewData.getLanguage());
//        oldBook.setPages(bookWithNewData.getPages());
//        oldBook.setPrice(bookWithNewData.getPrice());
//        oldBook.setTitle(bookWithNewData.getTitle());
//    }

}
