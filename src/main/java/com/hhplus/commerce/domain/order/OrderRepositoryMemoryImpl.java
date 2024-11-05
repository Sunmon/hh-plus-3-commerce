package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Repository
public class OrderRepositoryMemoryImpl {
    Map<Long, Order> db = new HashMap<>();
    AtomicLong atomicId = new AtomicLong(1L);

    public Optional<Order> findById(Long orderId) {
        return Optional.ofNullable(db.get(orderId));
    }

    public Order findByIdOrElseThrow(Long orderId) throws IllegalArgumentException {
        if (orderId > atomicId.get()) {
            throw new IllegalArgumentException("ORDER_NOT_FOUND");
        }
        return db.get(orderId);
    }

    public Order save(Order order) {
        order.assignId(nextId());
        db.put(order.getId(), order);
        return order;
    }

    private long nextId() {
        return atomicId.getAndIncrement();
    }
}
