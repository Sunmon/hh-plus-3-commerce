package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.common.exception.CustomException;
import com.hhplus.commerce.domain.stock.entity.StockHistory;
import com.hhplus.commerce.domain.stock.model.StockErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StockHistoryRepositoryImpl implements StockHistoryRepository {
    final StockHistoryJpaRepository stockHistoryJpaRepository;
    final StockRepositoryMemoryImpl stockRepositoryMemoryImpl;

    @Override
    public StockHistory save(StockHistory stockHistory) {
        return stockHistoryJpaRepository.save(stockHistory);
    }

    @Override
    public Optional<StockHistory> findById(Long id) {
        return stockHistoryJpaRepository.findById(id);
    }

    @Override
    public StockHistory findByIdOrElseThrow(Long id) throws IllegalArgumentException {
        return stockHistoryJpaRepository.findById(id).orElseThrow(() -> new CustomException(StockErrorCode.STOCK_HISTORY_NOT_FOUND));
    }

    @Override
    public List<StockHistory> findByTimestampBetween(LocalDateTime from, LocalDateTime to) {
        return null;
    }
}
