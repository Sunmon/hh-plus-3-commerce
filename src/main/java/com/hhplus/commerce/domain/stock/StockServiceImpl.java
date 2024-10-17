package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class StockServiceImpl implements StockService {
    final private StockRepository stockRepository;
    
    @Override
    public Stock getStockByProductId(Long productId) {
        return stockRepository.findByProductIdOrThrow(productId);
    }

    @Override
    public Stock decreaseStockByProductId(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductIdOrThrow(productId);
        stock.decrease(quantity);
        return stock;
    }
}
