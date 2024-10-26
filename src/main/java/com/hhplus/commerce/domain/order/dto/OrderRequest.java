package com.hhplus.commerce.domain.order.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(@NotNull Long accountId, @NotNull List<OrderItemRequest> orderItems) {

    public static OrderRequest of(Long accountId, List<OrderItemRequest> orderItems) {
        return new OrderRequest(accountId, orderItems);
    }
}
