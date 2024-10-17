package com.hhplus.commerce.domain.stock.entity;

import com.hhplus.commerce.domain.order.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class StockHistory {
    @Id
    private Long id;
    private Long orderId;
    private Long productId;
    private Long quantity;
    private Long price;
    private Long totalPrice;
    private OrderStatus orderStatus;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public static StockHistory of(Long id, Long orderId, Long productId, Long quantity, Long price, OrderStatus orderStatus) {
        return new StockHistory(id, orderId, productId, quantity, price, quantity * price, orderStatus, LocalDateTime.now(), LocalDateTime.now());
    }

    public static StockHistory of(Long id) {
        return new StockHistory(id, null, null, null, null, null, null, LocalDateTime.now(), LocalDateTime.now());
    }

    public StockHistory assignId(Long id) {
        this.id = id;
        return this;
    }
}
