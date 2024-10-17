package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.Stock;

public interface StockRepository {
    Stock insert(Stock stock);

    Stock findByProductIdOrThrow(Long productId) throws IllegalArgumentException;
}
