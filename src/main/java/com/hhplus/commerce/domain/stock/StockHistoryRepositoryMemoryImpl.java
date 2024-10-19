package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.StockHistory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Repository
public class StockHistoryRepositoryMemoryImpl implements StockHistoryRepository {
    private final Map<Long, StockHistory> stockHistories = new HashMap<>();
    AtomicLong atomicId = new AtomicLong(1L);

    @Override
    public StockHistory insert(StockHistory stockHistory) {
        stockHistory.assignId(nextId());
        stockHistories.put(stockHistory.getId(), stockHistory);
        return stockHistory;
    }

    @Override
    public StockHistory findByIdOrElseThrow(Long id) {
        return Optional.ofNullable(stockHistories.get(id))
                .orElseThrow(() -> new IllegalArgumentException("NOT_FOUND_STOCK_HISTORY"));
    }

    @Override
    public List<StockHistory> findByTimestampBetween(LocalDateTime from, LocalDateTime to) {
        return stockHistories.values().stream()
                .filter(stockHistory -> stockHistory.getCreatedAt().isAfter(from) && stockHistory.getCreatedAt().isBefore(to))
                .toList();
    }

    private long nextId() {
        return atomicId.getAndIncrement();
    }


}
