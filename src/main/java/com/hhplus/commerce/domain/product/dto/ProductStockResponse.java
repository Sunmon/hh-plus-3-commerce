package com.hhplus.commerce.domain.product.dto;

import com.hhplus.commerce.domain.stock.entity.Stock;

public record ProductStockResponse(Long productId,
                                   String name,
                                   Long price,
                                   Long stock,
                                   Boolean inStock

) {
    public ProductStockResponse(Stock stock) {
        this(stock.getProduct().getId(), stock.getProduct().getName(), stock.getProduct().getPrice(), stock.getStock(), stock.getInStock());
    }

}
