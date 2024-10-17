package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Product getProductWithStock(Long productId) throws IllegalArgumentException {
        return productRepository.findByIdOrThrow(productId);
    }
}
