package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
//    Product findByIdOrElseThrow(Long productId) throws IllegalArgumentException;

}
