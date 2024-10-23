package com.hhplus.commerce.domain.order.dto;

import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.entity.OrderItem;
import com.hhplus.commerce.domain.order.model.OrderItems;
import com.hhplus.commerce.domain.order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        @NotNull Long orderId,
        @NotNull Long userId,
        @NotNull OrderStatus status,
        @NotNull Long totalPrice,

        @NotNull LocalDateTime createdAt,
        @NotNull LocalDateTime updatedAt,
        List<OrderItem> orderItems
) {
    public OrderResponse(Order order, OrderItems orderItems) {
        this(order.getId(), order.getAccount().getUserId(), order.getStatus(), order.getTotalPrice(), order.getCreatedAt(), order.getUpdatedAt(), orderItems.getOrderItems());
//        this(order.getId(), order.getUserId(), order.getStatus(), order.getTotalPrice(), order.getCreatedAt(), order.getUpdatedAt(), List.of());
    }
}
