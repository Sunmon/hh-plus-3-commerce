package com.hhplus.commerce.domain.order.dto;

import java.util.List;

public record OrderRequest(Long accountId, List<OrderItemRequest> orderItems) {

    public static OrderRequest of(Long accountId, List<OrderItemRequest> orderItems) {
        return new OrderRequest(accountId, orderItems);
    }
}
