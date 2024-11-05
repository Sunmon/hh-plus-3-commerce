package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.product.entity.Product;
import com.hhplus.commerce.domain.stock.entity.Stock;

import java.util.List;
import java.util.Optional;

public interface StockRepository {
    Stock save(Stock stock);

    Stock findByIdOrElseThrow(Long stockId) throws IllegalArgumentException;

    List<Stock> findByProductIdIn(List<Long> productIds);

    Optional<Stock> findByProductId(Long productId);

    Stock findByProduct(Product product);
}
