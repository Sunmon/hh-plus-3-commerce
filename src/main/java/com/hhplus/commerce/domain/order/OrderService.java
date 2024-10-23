package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.dto.OrderItemRequest;
import com.hhplus.commerce.domain.order.dto.OrderRequest;
import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.model.OrderItems;
import com.hhplus.commerce.domain.order.model.OrderStatus;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order getOrder(Long orderId) throws IllegalArgumentException;

    Map<Order, OrderItems> order(Long acocuntId, List<OrderItemRequest> orderItemRequests);

    Order createOrder(Long accountId);

    // REVIEW - RequestDTO가 서비스까지 넘어와서 찝찝함
    Order order(OrderRequest orderRequest);

    Order updateOrderStatus(Order order, OrderStatus orderStatus);
}
