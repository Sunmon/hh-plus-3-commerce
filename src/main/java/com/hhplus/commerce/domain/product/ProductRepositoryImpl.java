package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.common.exception.CustomException;
import com.hhplus.commerce.domain.product.entity.Product;
import com.hhplus.commerce.domain.product.model.ProductErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductRepositoryMemoryImpl productRepositoryMemoryImpl;

    @Override
    public Optional<Product> findById(Long productId) {
        return productJpaRepository.findById(productId);
    }

    @Override
    public Product findByIdOrElseThrow(Long productId) throws IllegalArgumentException {
        return productJpaRepository.findById(productId).orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return productJpaRepository.saveAll(products);
    }

    @Override
    public List<Product> findAllByName(String name) {
        return productJpaRepository.findByName(name);
    }
}
