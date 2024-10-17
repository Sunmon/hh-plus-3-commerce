package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.order.OrderStatus;
import com.hhplus.commerce.domain.stock.entity.Stock;
import com.hhplus.commerce.domain.stock.entity.StockHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public
class StockServiceImpl implements StockService {
    final private StockRepository stockRepository;
    // 순환참조 일어날거같은데...
    final private StockHistoryRepository stockHistoryRepository;

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

    @Override
    public StockHistory insertHistory(Long productId, Long orderId, Long quantity, Long price, OrderStatus orderStatus) {
        StockHistory stockHistory = StockHistory.of(null, orderId, productId, quantity, price, orderStatus);
        return stockHistoryRepository.insert(stockHistory);
    }

    @Override
    public List<StockHistory> findByTimestampBetween(LocalDateTime from, LocalDateTime to) {
        return stockHistoryRepository.findByTimestampBetween(from, to);
    }

}
