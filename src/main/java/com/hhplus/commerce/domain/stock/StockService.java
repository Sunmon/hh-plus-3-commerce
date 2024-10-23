package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.order.model.OrderStatus;
import com.hhplus.commerce.domain.stock.entity.Stock;
import com.hhplus.commerce.domain.stock.entity.StockHistory;
import com.hhplus.commerce.domain.stock.model.Stocks;

import java.time.LocalDateTime;
import java.util.List;

public interface StockService {
    Stock getStockByProductId(Long productId) throws IllegalArgumentException;

    Stock decreaseStockByProductId(Long productId, Long quantity) throws IllegalArgumentException;

    Stocks getStocksByProductIds(List<Long> productIds);

    StockHistory insertHistory(Long productId, Long orderId, Long quantity, Long price, OrderStatus orderStatus);

    List<StockHistory> findByTimestampBetween(LocalDateTime from, LocalDateTime to);
}
