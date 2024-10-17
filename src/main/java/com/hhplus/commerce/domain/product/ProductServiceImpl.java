package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;
import com.hhplus.commerce.domain.stock.StockService;
import com.hhplus.commerce.domain.stock.entity.StockHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final StockService stockService;

    @Override
    @Transactional(readOnly = true)
    public Product getProductWithStock(Long productId) throws IllegalArgumentException {
        return productRepository.findByIdOrElseThrow(productId);
    }

    @Override
    public List<Product> getTopProducts(int num, LocalDateTime from, LocalDateTime to) {
        List<StockHistory> stockHistories = stockService.findByTimestampBetween(from, to);
        Map<Long, Long> productSales = stockHistories.stream().collect(
                Collectors.groupingBy(StockHistory::getProductId,
                        Collectors.summingLong(StockHistory::getQuantity)
                ));
        return productSales.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(num)
                .map(entry -> productRepository.findByIdOrElseThrow(entry.getKey()))
                .collect(Collectors.toList());
    }

    @Override
    public Product findById(Long productId) throws IllegalArgumentException {
        return productRepository.findByIdOrElseThrow(productId);
    }
}
