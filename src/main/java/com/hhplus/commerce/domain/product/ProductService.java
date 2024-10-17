package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;

public interface ProductService {
    Product getProductWithStock(Long productId) throws IllegalArgumentException;

    Product findById(Long productId) throws IllegalArgumentException;
}
