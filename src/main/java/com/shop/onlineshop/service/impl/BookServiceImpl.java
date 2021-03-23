package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.exception.BookNotFoundException;
import com.shop.onlineshop.mapper.BookAddMapper;
import com.shop.onlineshop.mapper.BookViewMapper;
import com.shop.onlineshop.model.binding.BookAddBindingModel;
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

    @Override
    public void initBooks() {

        if (bookRepository.count() == 0) {

            AuthorEntity rowling = authorService.findByName("Joanne Rowling");
            AuthorEntity tolkien = authorService.findByName("John Ronald Reuel Tolkien");
            AuthorEntity goodkind = authorService.findByName("Terry Goodkind");

            CategoryEntity fantasy = categoryService.findByName("Fantasy");
            CategoryEntity fiction = categoryService.findByName("Fiction");
            CategoryEntity romance = categoryService.findByName("Romance");
            CategoryEntity drama = categoryService.findByName("Drama");
            CategoryEntity thriller = categoryService.findByName("Thriller");

            BookEntity harryPotter = new BookEntity(
                    "Harry Potter and the Goblet of Fire",
                    300,
                    "English",
                    "some description",
                    BigDecimal.valueOf(20.30)
            );
            harryPotter.setAuthor(rowling);

            harryPotter.setMainCategory(fantasy);
            Set<CategoryEntity> harryPotterCategories = new HashSet<>();
            harryPotterCategories.add(drama);
            harryPotterCategories.add(thriller);
            harryPotterCategories.add(fiction);
            harryPotter.setSubCategories(harryPotterCategories);

            List<PictureEntity> harryPotterPictureUrls = new ArrayList<>();
            harryPotterPictureUrls.add(new PictureEntity("https://images-na.ssl-images-amazon.com/images/I/81t2CVWEsUL.jpg"));
            harryPotter.setPictureUrls(harryPotterPictureUrls);
            bookRepository.save(harryPotter);

            BookEntity lordOfTheRings = new BookEntity(
                    "The Lord of the Rings: The return of the kind",
                    600,
                    "English",
                    "some other description",
                    BigDecimal.valueOf(30.10)
            );
            lordOfTheRings.setAuthor(tolkien);

            lordOfTheRings.setMainCategory(fantasy);
            Set<CategoryEntity> lordOfTheRingsCategories = new HashSet<>();
            lordOfTheRingsCategories.add(fiction);
            lordOfTheRingsCategories.add(drama);
            lordOfTheRings.setSubCategories(lordOfTheRingsCategories);

            List<PictureEntity> lordOfTheRingsUrls = new ArrayList<>();
            lordOfTheRingsUrls.add(new PictureEntity("https://images-na.ssl-images-amazon.com/images/I/51M708KEH5L.jpg"));
            lordOfTheRingsUrls.add(new PictureEntity("https://images-na.ssl-images-amazon.com/images/I/41Cqh4aThYL.jpg"));
            lordOfTheRings.setPictureUrls(lordOfTheRingsUrls);

            bookRepository.save(lordOfTheRings);

            BookEntity swordOfTruth = new BookEntity(
                    "The sword of Truth: Warheart",
                    400,
                    "English",
                    "yet another description",
                    BigDecimal.valueOf(40.30)
            );
            swordOfTruth.setAuthor(goodkind);

            swordOfTruth.setMainCategory(fantasy);
            Set<CategoryEntity> swordOfTruthCategories = new HashSet<>();
            swordOfTruthCategories.add(fiction);
            swordOfTruthCategories.add(drama);
            swordOfTruthCategories.add(romance);
            swordOfTruth.setSubCategories(swordOfTruthCategories);

            List<PictureEntity> swordOfTruthPictureUrls = new ArrayList<>();
            swordOfTruthPictureUrls.add(new PictureEntity("https://images-na.ssl-images-amazon.com/images/I/512wolRCzHL.jpg"));
            swordOfTruthPictureUrls.add(new PictureEntity("https://images-na.ssl-images-amazon.com/images/I/317nwKiMuFL.jpg"));
            swordOfTruth.setPictureUrls(swordOfTruthPictureUrls);

            bookRepository.save(swordOfTruth);
        }
    }

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
    public void addBook(BookAddBindingModel bookAddBindingModel) {

        BookEntity book = bookAddMapper.BookAddBindingToBookEntity(bookAddBindingModel);
        bookRepository.save(book);

    }
}
