package com.shop.onlineshop.model.binding;

import com.shop.onlineshop.model.entity.AuthorEntity;
import com.shop.onlineshop.model.entity.CategoryEntity;
import com.shop.onlineshop.model.entity.PictureEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class BookAddBindingModel {

    @Size(max = 255, message = "Size cannot exceed 255 characters")
    @NotBlank(message = "title cannot be empty")
    private String title;

    @Size(max = 3000, message = "Pages cannot exceed 3000")
    private int pages;

    @NotBlank(message = "language cannot be empty")
    private String language;

    @NotBlank(message = "description cannot be empty")
    private String description;

    @DecimalMin(value = "0", message = "Price must be positive")
    private BigDecimal price;

    private List<PictureEntity> pictureUrls;

    private AuthorEntity author;

    private CategoryEntity mainCategory;

    private Set<CategoryEntity> subCategories;
}
