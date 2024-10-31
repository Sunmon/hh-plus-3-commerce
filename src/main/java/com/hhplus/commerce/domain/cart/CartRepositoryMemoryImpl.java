package com.hhplus.commerce.domain.cart;

import com.hhplus.commerce.domain.cart.entity.Cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class CartRepositoryMemoryImpl implements CartRepository {

    Map<Long, Cart> db = new HashMap<>();
    AtomicLong atomicId = new AtomicLong(1L);


    private long nextId() {
        return atomicId.getAndIncrement();
    }

    @Override
    public Cart insert(Cart cart) {
        cart.assignId(nextId());
        db.put(cart.getId(), cart);
        return cart;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Cart> findAllByUserId(Long userId) {
        return db.entrySet().stream().filter(entry -> entry.getValue().getUserId().equals(userId)).map(Map.Entry::getValue).toList();
    }

    @Override
    public Boolean deleteAllByUserId(Long userId) {
        return null;
    }
}
