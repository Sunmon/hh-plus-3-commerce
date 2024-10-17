package com.hhplus.commerce.domain.cart;

import com.hhplus.commerce.domain.cart.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;


    @Override
    public Cart addProductToCart(Long userId, Long productId, Long quantity) {
        return cartRepository.insert(Cart.of(userId, productId, quantity));
    }

    @Override
    public boolean clearCart(Long cartId) {

        return false;
    }

    @Override
    public Cart getCart(Long cartId) {
        return cartRepository.selectById(cartId);
    }

    @Override
    public List<Cart> getCartsByUserId(Long userId) {
        return cartRepository.selectAllByUserId(userId);
    }
}
