package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {


    List<Product> findByName(String name);
}
