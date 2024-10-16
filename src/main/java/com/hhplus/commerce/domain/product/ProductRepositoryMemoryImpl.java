package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
class ProductRepositoryMemoryImpl implements ProductRepository {
    Map<Long, Product> db = new HashMap<>();
    AtomicLong atomicId = new AtomicLong(1L);

    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.ofNullable(db.get(productId));
    }

    @Override
    public Product findByIdOrThrow(Long productId) throws IllegalArgumentException {
        if (productId > atomicId.get()) {
            throw new IllegalArgumentException("PRODUCT_NOT_FOUND");
        }
        return db.get(productId);
    }

    @Override
    public Product insert(Product product) {
        product.assignId(nextId());
        db.put(product.getId(), product);
        return product;
    }

    private long nextId() {
        return atomicId.getAndIncrement();
    }
}
