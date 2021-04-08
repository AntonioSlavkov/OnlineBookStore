package com.shop.onlineshop.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AuthorEntity extends BaseEntity{

    @Column(name = "name", unique = true, nullable = false)
    private String author;


}
