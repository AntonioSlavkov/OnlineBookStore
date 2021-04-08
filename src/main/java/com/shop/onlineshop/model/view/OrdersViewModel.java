package com.shop.onlineshop.model.view;

import com.shop.onlineshop.model.entity.BookEntity;
import com.shop.onlineshop.model.entity.UserEntity;
import com.shop.onlineshop.model.entity.enums.StatusName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrdersViewModel {



    private UserEntity user;
    private List<BookEntity> books;
    private StatusName status;
}
