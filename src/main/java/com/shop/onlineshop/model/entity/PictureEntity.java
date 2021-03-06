package com.shop.onlineshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PictureEntity extends BaseEntity {

    @Column(name = "picture_url")
    private String imageUrl;

    @ManyToOne(cascade = CascadeType.DETACH)
    private BookEntity bookEntity;
}
