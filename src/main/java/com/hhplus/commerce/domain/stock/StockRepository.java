package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.Stock;

import java.util.List;
import java.util.Optional;

public interface StockRepository {
    Stock insert(Stock stock);

    Stock findByIdOrElseThrow(Long stockId) throws IllegalArgumentException;

    List<Stock> findByProductIdIn(List<Long> productIds);

    Optional<Stock> findByProductId(Long productId);
}
