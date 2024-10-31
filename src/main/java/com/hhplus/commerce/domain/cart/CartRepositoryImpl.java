package com.hhplus.commerce.domain.cart;

import com.hhplus.commerce.domain.cart.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {
    CartRepositoryMemoryImpl cartRepositoryMemory;
    CartJpaRepository cartJpaRepository;

    @Override
    public Cart insert(Cart cart) {
        return cartJpaRepository.save(cart);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartJpaRepository.findById(id);
    }

    @Override
    public List<Cart> findAllByUserId(Long userId) {
        return cartJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Boolean deleteAllByUserId(Long userId) {
        return cartJpaRepository.deleteAllByUserId(userId);
    }
}
