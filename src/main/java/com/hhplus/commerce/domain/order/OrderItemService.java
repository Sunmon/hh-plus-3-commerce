package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.entity.OrderItem;
import com.hhplus.commerce.domain.product.entity.Product;

public interface OrderItemService {
    OrderItem createOrderItem(Long productId, Long quantity);

    OrderItem createOrderItem(Long orderId, Long productId, Long quantity);

    OrderItem createOrderItem(Order order, Long productId, Long quantity);

    OrderItem createOrderItem(Order order, Product product, Long quantity);

    OrderItem findById(Long orderItemId);

    OrderItem findByProductIdOrElseThrow(Long productId) throws IllegalArgumentException;
//    OrderItem insert(OrderItem orderItem);
//    List<OrderItem> saveAll(List<OrderItem> orderItems);
//    List<OrderItem> findByOrderId(Long orderId);

}
