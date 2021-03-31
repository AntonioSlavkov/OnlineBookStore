package com.shop.onlineshop.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
public class UserContactEntity extends BaseEntity {

    @Column(name = "phone_number")
    private Long phoneNumber;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    @Column(name = "postal_code")
    private Long postalCode;
}
