package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface StockJpaRepository extends JpaRepository<Stock, Long> {
//    Stock findByProductIdOrElseThrow(Long productId) throws IllegalArgumentException;
}
