package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long productId);

    Product findByIdOrThrow(Long productId) throws IllegalArgumentException;

    Product insert(Product product);
}
