package com.shop.onlineshop.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

//    @OneToMany
//    private List<CartEntity> cart = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserContactEntity userContactEntity;

    public UserEntity addRole(RoleEntity roleEntity) {
        this.roles.add(roleEntity);
        return this;
    }

    public UserEntity deleteRole (RoleEntity roleEntity) {
        this.roles.remove(roleEntity);
        return this;
    }

//    public UserEntity addToCart (CartEntity cartEntity) {
//        this.cart.add(cartEntity);
//        return this;
//    }

}
