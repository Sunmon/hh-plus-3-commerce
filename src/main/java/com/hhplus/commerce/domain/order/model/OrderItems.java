package com.hhplus.commerce.domain.order.model;

import com.hhplus.commerce.domain.order.entity.OrderItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderItems {
    private final List<OrderItem> orderItems;

    static public OrderItems of(List<OrderItem> orderItems) {
        return new OrderItems(orderItems);
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
