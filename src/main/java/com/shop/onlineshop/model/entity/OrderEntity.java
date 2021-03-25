package com.shop.onlineshop.model.entity;

import com.shop.onlineshop.model.entity.enums.StatusName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity extends BaseEntity {

    @ManyToOne
    private UserEntity user;

    @OneToMany
    private List<BookEntity> books;

    private StatusName status;
}