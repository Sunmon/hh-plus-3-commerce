package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderItemRepositoryMemoryImpl implements OrderItemRepository {
    Map<Long, OrderItem> db = new HashMap<>();
    AtomicLong atomicId = new AtomicLong(1L);

    @Override
    public Optional<OrderItem> findById(Long orderItemId) {
        return Optional.ofNullable(db.get(orderItemId));
    }

    @Override
    public OrderItem findByProductId(Long productId) throws IllegalArgumentException {
        return db.get(productId);
    }


    @Override
    public List<OrderItem> findAllByOrderId(Long orderId) {
        return db.values().stream().filter(orderItem -> orderItem.getOrder().getId().equals(orderId)).toList();
    }

    @Override
    public OrderItem insert(OrderItem orderItem) {
        orderItem.assignId(nextId());
        db.put(orderItem.getId(), orderItem);
        return orderItem;
    }

    @Override
    public List<OrderItem> saveAll(List<OrderItem> orderItems) {
        List<Long> ids = new ArrayList<>();
        orderItems.forEach(orderItem -> {
            OrderItem newItem = db.getOrDefault(orderItem.getId(), orderItem.assignId(nextId()));
            db.put(newItem.getId(), newItem);
            ids.add(newItem.getId());
        });
        return db.entrySet().stream().filter(entry -> ids.contains(entry.getKey())).map(Map.Entry::getValue).toList();
    }


    private long nextId() {
        return atomicId.getAndIncrement();
    }

}
