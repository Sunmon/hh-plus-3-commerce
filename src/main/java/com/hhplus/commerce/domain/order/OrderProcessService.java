package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.dto.OrderItemRequest;
import com.hhplus.commerce.domain.order.dto.OrderItems;
import com.hhplus.commerce.domain.order.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderProcessService {
    Map<Order, OrderItems> processOrder();

    Map<Order, OrderItems> processOrder(Long accountId, List<OrderItemRequest> orderItemRequests);
//    Map<Order, OrderItems> processOrder(Long accountId, Long productId, long quantity);
}
