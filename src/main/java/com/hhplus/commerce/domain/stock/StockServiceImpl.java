package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public
class StockServiceImpl implements StockService {
    final private StockRepository stockRepository;

    @Override
    public Stock getStockByProductId(Long productId) {
        return stockRepository.findByProductIdOrElseThrow(productId);
    }

    @Override
    public Stock decreaseStockByProductId(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductIdOrElseThrow(productId);
        stock.decrease(quantity);
        return stock;
    }

    @Override
    public Stocks getStocksByProductIds(List<Long> productIds) {
        return new Stocks(stockRepository.findByProductIdIn(productIds));
    }
}
