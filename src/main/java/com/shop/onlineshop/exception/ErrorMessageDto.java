package com.shop.onlineshop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessageDto {

    private int errorCode;
    private String errorMessage;
}
