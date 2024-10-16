package com.hhplus.commerce.domain.order.dto;

import com.hhplus.commerce.domain.order.OrderStatus;
import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.entity.OrderItem;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        @NotNull Long orderId,
        @NotNull Long userId,
        @NotNull LocalDateTime createdAt,
        @NotNull LocalDateTime updatedAt,
        @NotNull OrderStatus status,
        @NotNull Long totalPrice,
        List<OrderItem> orderItems
) {
    public OrderResponse(Order order) {
        this(order.getId(), order.getUserId(), order.getCreatedAt(), order.getUpdatedAt(), order.getStatus(), order.getTotalPrice(), order.getOrderItems());
    }
}
