package com.hhplus.commerce.domain.stock;

import com.hhplus.commerce.domain.stock.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryJpaRepository extends JpaRepository<StockHistory, Long> {

}