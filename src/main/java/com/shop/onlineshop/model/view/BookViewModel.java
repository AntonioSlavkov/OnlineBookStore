package com.shop.onlineshop.model.view;

import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.entity.PictureEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookViewModel {

    private Long id;

    private String title;

    private int pages;

    private String language;

    private String description;

    private BigDecimal price;

    private List<PictureEntity> pictureUrls;

    private AuthorEntity author;

    private CategoryEntity mainCategory;

    private Set<CategoryEntity> subCategories;
}
