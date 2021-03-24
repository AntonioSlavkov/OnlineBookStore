package com.shop.onlineshop.service.impl;

import com.shop.onlineshop.exception.AuthorNotFoundException;
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
    @Transactional
    public void addBook(BookAddBindingModel bookAddBindingModel) {

        BookEntity book = bookAddMapper.BookAddBindingToBookEntity(bookAddBindingModel);

        /**
         * Sets the author to the entity and adds the author if he does not exist in the database
         */
        String author = bookAddBindingModel.getAuthor().getName();

        if (authorService.findByName(author).getName() == null) {
            authorService.saveAuthor(bookAddBindingModel.getAuthor());
        }
        book.setAuthor(authorService.findByName(author));


        /**
         * Sets the main category of the book and throws an exception if the category is invalid
         */
        String mainCategory = bookAddBindingModel.getMainCategory().getName();

        if (categoryService.findByName(mainCategory).getName() == null) {
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

                if (categoryService.findByName(bindingSubCategory.getName()) == null) {
                    categoryService.saveCategory(bindingSubCategory);
                }
                    subCategories.add(categoryService.findByName(bindingSubCategory.getName()));
            }
        }
        book.setSubCategories(subCategories);


        bookRepository.save(book);

    }

    @Override
    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book does not exist.", HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBook(Long id, BookUpdateBindingModel bookUpdateBindingModel) {
        //TODO implement or delete the update method
        BookEntity oldBook = bookRepository
                .findById(id).orElseThrow( () -> new BookNotFoundException(
                        "Book does not exist.",
                        HttpStatus.NOT_FOUND));

        BookEntity bookWithNewData = bookUpdateMapper.bookUpdateBindingToBookEntity(bookUpdateBindingModel);

        oldBook.setAuthor(bookWithNewData.getAuthor());
        oldBook.setMainCategory(bookWithNewData.getMainCategory());
        oldBook.setSubCategories(bookWithNewData.getSubCategories());
        oldBook.setPictureUrls(bookWithNewData.getPictureUrls());
        oldBook.setDescription(bookWithNewData.getDescription());
        oldBook.setLanguage(bookWithNewData.getLanguage());
        oldBook.setPages(bookWithNewData.getPages());
        oldBook.setPrice(bookWithNewData.getPrice());
        oldBook.setTitle(bookWithNewData.getTitle());
    }

}
