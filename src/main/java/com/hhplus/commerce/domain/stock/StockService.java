package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.Stock;

public interface StockService {
    Stock getStockByProductId(Long productId) throws IllegalArgumentException;

    Stock decreaseStockByProductId(Long productId, Long quantity) throws IllegalArgumentException;
}
