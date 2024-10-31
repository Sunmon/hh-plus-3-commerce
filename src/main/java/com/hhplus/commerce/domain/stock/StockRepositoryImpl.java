package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.common.exception.CustomException;
import com.hhplus.commerce.domain.stock.entity.Stock;
import com.hhplus.commerce.domain.stock.model.StockErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class StockRepositoryImpl implements StockRepository {

    final StockRepositoryMemoryImpl stockRepositoryMemory;
    final StockJpaRepository stockJpaRepository;

    @Override
    public Stock save(Stock stock) {
        return stockJpaRepository.save(stock);
    }

    @Override
    public Stock findByIdOrElseThrow(Long stockId) throws IllegalArgumentException {
        return stockJpaRepository.findById(stockId).orElseThrow(() -> new CustomException(StockErrorCode.STOCK_NOT_FOUND));
    }

    @Override
    public List<Stock> findByProductIdIn(List<Long> productIds) {
        return stockJpaRepository.findByProduct_IdIn(productIds);
    }

    @Override
    public Optional<Stock> findByProductId(Long productId) {
        return stockJpaRepository.findByProduct_Id(productId);
    }
}
