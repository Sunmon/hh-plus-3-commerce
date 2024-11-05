package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.product.entity.Product;
import com.hhplus.commerce.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface StockJpaRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProduct_Id(Long productId);

    List<Stock> findByProduct_IdIn(List<Long> productIds);

    Stock findByProduct(Product product);

//    List<Stock> findByProductIdIn(List<Long> productIds);
//
//    Optional<Stock> findByProductId(Long productId);
}
