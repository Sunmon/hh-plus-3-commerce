package com.hhplus.commerce.domain.order.dto;

import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(@NotNull Long productId, @NotNull Long quantity) {
    public static OrderItemRequest of(Long productId, Long quantity) {
        return new OrderItemRequest(productId, quantity);
    }
}
