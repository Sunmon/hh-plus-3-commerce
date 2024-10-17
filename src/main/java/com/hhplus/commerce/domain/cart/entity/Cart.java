package com.hhplus.commerce.domain.cart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
//@RequiredArgsConstructor
@NoArgsConstructor
@Getter
public class Cart {
    @Id
    private Long id;
    private Long userId;
    private Long productId;
    private Long quantity;

    public static Cart of(Long userId, Long productId, Long quantity) {
        return new Cart(null, userId, productId, quantity);
    }

    public void assignId(Long id) {
        this.id = id;
    }
}
