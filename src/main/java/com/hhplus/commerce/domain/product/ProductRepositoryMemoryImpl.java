package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepositoryMemoryImpl implements ProductRepository {
    Map<Long, Product> db = new HashMap<>();
    AtomicLong atomicId = new AtomicLong(1L);

    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.ofNullable(db.get(productId));
    }

    @Override
    public Product findByIdOrElseThrow(Long productId) throws IllegalArgumentException {
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

    @Override
    public List<Product> saveAll(List<Product> products) {
        List<Long> ids = new ArrayList<>();
        products.forEach(product -> {
            Product newItem = db.getOrDefault(product.getId(), product.assignId(nextId()));
            db.put(newItem.getId(), newItem);
            ids.add(newItem.getId());
        });
        return db.entrySet().stream().filter(entry -> ids.contains(entry.getKey())).map(Map.Entry::getValue).toList();
    }

    private long nextId() {
        return atomicId.getAndIncrement();
    }
}
