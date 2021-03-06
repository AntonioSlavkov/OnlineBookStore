package com.shop.onlineshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookEntity extends BaseEntity {

    @Column(name = "titles", unique = true, nullable = false)
    private String title;

    @Column(name = "pages")
    private int pages;

    @Column(name = "languages")
    private String language;

    @Column(name = "descriptions", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PictureEntity> pictureUrls;

    @ManyToOne
    private AuthorEntity author;

    @ManyToOne
    private CategoryEntity mainCategory;

    @ManyToMany
    private Set<CategoryEntity> subCategories;

    public BookEntity(String title, int pages, String language, String description, BigDecimal price) {
        this.title = title;
        this.pages = pages;
        this.language = language;
        this.description = description;
        this.price = price;
    }
}
