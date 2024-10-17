package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long productId);

    Product findByIdOrThrow(Long productId) throws IllegalArgumentException;

    Product insert(Product product);

    List<Product> saveAll(List<Product> products);
}
