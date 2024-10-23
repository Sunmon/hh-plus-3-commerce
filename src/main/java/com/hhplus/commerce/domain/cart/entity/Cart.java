package com.hhplus.commerce.domain.cart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니 엔티티
 * 프론트에서 '장바구니 추가' -> Cart에 id, userId, productId, quantity 저장
 * 프론트에서 '장바구니 주문' -> Cart에서 userId로 조회하여 productId, quantity 조회 후 OrderItem에 저장. cart에 있는 아이템 삭제.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "carts")
public class Cart {
    @Id
    private Long id;

    // user는 본 서비스에서 사용하지 않기 때문에 빠른 개발을 위해 연관관계를 맺지 않음.
    private Long userId;

    // 빠른 개발을 위해 연관관계를 맺지 않음
    private Long productId;
    private Long quantity;

    public static Cart of(Long userId, Long productId, Long quantity) {
        return new Cart(null, userId, productId, quantity);
    }

    public void assignId(Long id) {
        this.id = id;
    }
}
