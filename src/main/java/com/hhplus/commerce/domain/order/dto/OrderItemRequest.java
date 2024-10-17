package com.hhplus.commerce.domain.order.dto;

public record OrderItemRequest(Long productId, Long quantity) {
    public static OrderItemRequest of(long productId, long quantity) {
        return new OrderItemRequest(productId, quantity);
    }
}
