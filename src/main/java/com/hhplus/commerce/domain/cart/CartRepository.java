package com.hhplus.commerce.domain.cart;

import com.hhplus.commerce.domain.cart.entity.Cart;

import java.util.List;

public interface CartRepository {
    Cart insert(Cart cart);

    Cart selectById(Long id);

    List<Cart> selectAllByUserId(Long userId);

    Boolean deleteAllByUserId(Long userId);
}
