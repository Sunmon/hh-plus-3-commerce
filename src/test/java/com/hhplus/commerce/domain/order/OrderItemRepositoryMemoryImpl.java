package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.OrderItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class OrderItemRepositoryMemoryImpl implements OrderItemRepository {
    Map<Long, OrderItem> db = new HashMap<>();
    AtomicLong atomicId = new AtomicLong(1L);

    @Override
    public Optional<OrderItem> findById(Long orderItemId) {
        return Optional.ofNullable(db.get(orderItemId));
    }

    @Override
    public OrderItem findByIdOrThrow(Long productId) throws IllegalArgumentException {
        if (productId > atomicId.get()) {
            throw new IllegalArgumentException("PRODUCT_NOT_FOUND");
        }
        return db.get(productId);
    }

    @Override
    public OrderItem insert(OrderItem orderItem) {
        orderItem.assignId(nextId());
        db.put(orderItem.getId(), orderItem);
        return orderItem;
    }


    private long nextId() {
        return atomicId.getAndIncrement();
    }

}
