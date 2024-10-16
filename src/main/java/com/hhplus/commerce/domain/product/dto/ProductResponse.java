package com.hhplus.commerce.domain.product.dto;

import com.hhplus.commerce.domain.product.entity.Product;

public record ProductResponse(Long productId,
                              String name,
                              Long price,
                              Long stock,
                              Boolean inStock

) {
    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getStock(), product.getInStock());
    }

}
