package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.OrderItem;

import java.util.Optional;

public interface OrderItemRepository {

    Optional<OrderItem> findById(Long orderItemId);

    OrderItem findByIdOrThrow(Long productId) throws IllegalArgumentException;

    public OrderItem insert(OrderItem orderItem);
}
