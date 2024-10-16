package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;

public interface ProductService {
    Product getProduct(Long productId) throws IllegalArgumentException;
}
