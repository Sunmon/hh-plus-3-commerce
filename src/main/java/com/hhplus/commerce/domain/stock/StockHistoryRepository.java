package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.StockHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface StockHistoryRepository {
    StockHistory insert(StockHistory stockHistory);

    StockHistory findByIdOrElseThrow(Long id) throws IllegalArgumentException;

    List<StockHistory> findByTimestampBetween(LocalDateTime from, LocalDateTime to);
}
