package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.Order;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Long orderId);

    Order findByIdOrThrow(Long orderId) throws IllegalArgumentException;

    Order insert(Order order);

}
