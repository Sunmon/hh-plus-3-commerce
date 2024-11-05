package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Long orderId);

    Order findByIdOrElseThrow(Long orderId) throws IllegalArgumentException;

    Order save(Order order);

    List<Order> findAllByAccountId(Long accountId);
}
