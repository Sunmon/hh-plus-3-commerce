package com.hhplus.commerce.domain.order.dto;

import com.hhplus.commerce.domain.order.entity.OrderItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderItems {
    private final List<OrderItem> orderItems;

    static public OrderItems of(List<OrderItemRequest> orderItemRequests) {
        return new OrderItems(orderItemRequests.stream()
                .map(request -> OrderItem.of(request.productId(), request.quantity()))
                .toList());
    }

    public Long getTotalPrice() {
        return orderItems.stream()
                .mapToLong(OrderItem::getTotalPrice)
                .sum();
    }

    public List<Long> getProductIds() {
        return orderItems.stream()
                .mapToLong(orderItem -> orderItem.getProduct().getId()).boxed().toList();
    }
}
