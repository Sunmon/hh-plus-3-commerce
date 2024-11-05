package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository {

    Optional<OrderItem> findById(Long orderItemId);

    List<OrderItem> findByProductId(Long productId) throws IllegalArgumentException;

    public OrderItem save(OrderItem orderItem);

    List<OrderItem> saveAll(List<OrderItem> orderItems);


    List<OrderItem> findAllByOrderId(Long orderId);


    List<OrderItem> findAllByProductId(Long productId);
}
