package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.Stock;

import java.util.List;

public interface StockService {
    Stock getStockByProductId(Long productId) throws IllegalArgumentException;

    Stock decreaseStockByProductId(Long productId, Long quantity) throws IllegalArgumentException;

    Stocks getStocksByProductIds(List<Long> productIds);


}
