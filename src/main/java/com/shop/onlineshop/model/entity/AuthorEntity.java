package com.shop.onlineshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "name")
    private String name;


}
