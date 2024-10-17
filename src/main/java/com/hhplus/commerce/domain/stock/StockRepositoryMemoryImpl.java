package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.Stock;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public
class StockRepositoryMemoryImpl implements StockRepository {
    Map<Long, Stock> db = new HashMap<>();
    AtomicLong atomicId = new AtomicLong(1L);

    @Override
    public Stock insert(Stock stock) {
        stock.assignId(nextId());
        db.put(stock.getId(), stock);
        return stock;
    }

    @Override
    public Stock findByProductIdOrElseThrow(Long productId) throws IllegalArgumentException {
        return db.values().stream().filter(stock -> stock.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new IllegalArgumentException("PRODUCT_NOT_FOUND"));
    }

    @Override
    public List<Stock> findByProductIdIn(List<Long> productIds) {
        return null;
    }

    private long nextId() {
        return atomicId.getAndIncrement();
    }
}
