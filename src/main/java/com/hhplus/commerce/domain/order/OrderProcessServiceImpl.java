package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.account.AccountService;
import com.hhplus.commerce.domain.order.dto.OrderItemRequest;
import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.entity.OrderItem;
import com.hhplus.commerce.domain.order.model.OrderItems;
import com.hhplus.commerce.domain.order.model.OrderStatus;
import com.hhplus.commerce.domain.stock.StockService;
import com.hhplus.commerce.domain.stock.model.Stocks;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderProcessServiceImpl implements OrderProcessService {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final StockService stockService;
    private final AccountService accountService;

    @Override
    public Map<Order, OrderItems> processOrder() {
        return null;
    }

    @Override
    @Transactional
    public Map<Order, OrderItems> processOrder(Long accountId, List<OrderItemRequest> orderItemRequests) throws RuntimeException {
        Order order = orderService.createOrder(accountId);
        OrderItems orderItems = orderItemService.createOrderItems(order.getId(), orderItemRequests);

        // 재고 업데이트
        try {
            Stocks stocks = stockService.getStocksByProductIds(orderItems.getProductIds());
            stocks.decreaseStocks(orderItems);
            orderService.updateOrderStatus(order, OrderStatus.ORDER_SUCCESS);
        } catch (RuntimeException e) {
            orderService.updateOrderStatus(order, OrderStatus.ORDER_FAILED);
            throw e;
        } finally {
            for (OrderItem orderItem : orderItems.getOrderItems()) {
                stockService.insertHistory(order.getId(), orderItem.getProduct().getId(), orderItem.getQuantity(), orderItem.getPrice(), order.getStatus());
            }
        }

        // 계좌 업데이트
        try {
            accountService.withdraw(accountId, orderItems.getTotalPrice());
            orderService.updateOrderStatus(order, OrderStatus.PAYMENT_SUCCESS);
        } catch (RuntimeException e) {
            orderService.updateOrderStatus(order, OrderStatus.PAYMENT_FAILED);
            throw e;
        }

        orderService.updateOrderStatus(order, OrderStatus.ORDER_SUCCESS);
        // 외부 API 호출
//        callExternalAPI(order, orderItems);
        return Map.of(order, orderItems);
    }
}
