package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.Order;

public interface OrderService {
    Order getOrder(Long orderId) throws IllegalArgumentException;
}
