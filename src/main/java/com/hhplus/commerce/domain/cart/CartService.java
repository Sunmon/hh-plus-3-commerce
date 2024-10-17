package com.hhplus.commerce.domain.cart;

import com.hhplus.commerce.domain.cart.entity.Cart;

import java.util.List;

public interface CartService {
    Cart addProductToCart(Long userId, Long productId, Long quantity);

    boolean clearCart(Long cartId);

    Cart getCart(Long cartId);

    List<Cart> getCartsByUserId(Long userId);

}
