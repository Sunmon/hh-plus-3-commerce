package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.Stock;

import java.util.List;

public interface StockRepository {
    Stock insert(Stock stock);

    Stock findByProductIdOrElseThrow(Long productId) throws IllegalArgumentException;

    List<Stock> findByProductIdIn(List<Long> productIds);
}
