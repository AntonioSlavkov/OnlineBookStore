package com.shop.onlineshop.security.jwt;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JwtUtils.class})
@ExtendWith(SpringExtension.class)
public class JwtUtilsTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void testValidateJwtToken() {
        assertFalse(this.jwtUtils.validateJwtToken("ABC123"));
    }
}

