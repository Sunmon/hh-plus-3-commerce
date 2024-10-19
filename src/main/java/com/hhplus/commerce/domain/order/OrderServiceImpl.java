package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.dto.OrderItemRequest;
import com.hhplus.commerce.domain.order.dto.OrderItems;
import com.hhplus.commerce.domain.order.dto.OrderRequest;
import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.entity.OrderItem;
import com.hhplus.commerce.domain.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) throws IllegalArgumentException {
        Order order = orderRepository.findByIdOrElseThrow(orderId);
//        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return order;
    }

    @Override
    // 아직 주문이 진행되지 않은 아이템임을 명확히 하기 위해 RequestDTO를 사용
    public Map<Order, OrderItems> order(Long accountId, List<OrderItemRequest> orderItemRequests) {
//        return createOrder(accountId, orderItems);
//        Order order = createOrder(accountId, null);
        return null;
    }

    @Override
    public Order createOrder(Long accountId) {
        return orderRepository.insert(Order.of(null, accountId, OrderStatus.PENDING, 0L));
    }

//    @Transactional
//    private Map<Order, OrderItems> createOrder(Long accountId, OrderItems orderItems) {
//        Order order = orderRepository.insert(Order.of(null, accountId, OrderStatus.PENDING, orderItems.getTotalPrice()));
////        List<OrderItem> savedOrderItems = orderItemRepository.saveAll(orderItems.getOrderItems());
//        return Map.of(order, new OrderItems(savedOrderItems));
//    }

    @Transactional
    public Order updateOrderStatus(Order order, OrderStatus orderStatus) {
        order.updateStatus(orderStatus);
        return order;
    }

    private Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws IllegalArgumentException {
        Order order = orderRepository.findByIdOrElseThrow(orderId);
        order.updateStatus(orderStatus);
        return order;
    }

    @Override
    public Order order(OrderRequest orderRequest) {
        // 1. 재고 업데이트 진행
        // 2. 결제 진행
        return null;
    }

    public List<Stock> getStocks(List<OrderItem> orderItems) {

        return null;
    }
}
