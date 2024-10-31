package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.StockHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StockHistoryRepository {
    StockHistory save(StockHistory stockHistory);

    Optional<StockHistory> findById(Long id);

    StockHistory findByIdOrElseThrow(Long id) throws IllegalArgumentException;

    List<StockHistory> findByTimestampBetween(LocalDateTime from, LocalDateTime to);
}
