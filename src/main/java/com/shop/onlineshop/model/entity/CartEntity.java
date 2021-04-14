package com.shop.onlineshop.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Carts")
public class CartEntity extends BaseEntity {

    private Long bookId;
    private Long userId;
}
