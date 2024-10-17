package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.order.dto.OrderItems;
import com.hhplus.commerce.domain.stock.entity.Stock;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Stocks {
    private final List<Stock> stocks;

    public boolean hasOutOfStock() {
        return stocks.stream().anyMatch(stock -> stock.getStock() <= 0 || !stock.isInStock());
    }

    public void decreaseStocks(OrderItems orderItems) throws IllegalArgumentException {
        orderItems.getOrderItems().forEach(orderItem -> {
            Stock stock = stocks.stream()
                    .filter(s -> s.getProduct().getId().equals(orderItem.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("STOCK_NOT_FOUND"));
            stock.decrease(orderItem.getQuantity());
        });
    }
}
