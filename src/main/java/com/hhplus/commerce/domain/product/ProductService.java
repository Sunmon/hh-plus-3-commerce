package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductService {
    Product getProductWithStock(Long productId) throws IllegalArgumentException;

    List<Product> getTopProducts(int rank, LocalDateTime from, LocalDateTime to);

    Product findById(Long productId) throws IllegalArgumentException;
}
