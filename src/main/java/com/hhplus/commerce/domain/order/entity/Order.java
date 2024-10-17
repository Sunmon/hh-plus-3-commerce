package com.hhplus.commerce.domain.order.entity;

import com.hhplus.commerce.domain.order.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    private Long accountId;
    private OrderStatus status;
    private Long totalPrice;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public static Order of(Long orderId, Long accountId, OrderStatus orderStatus) {
        return new Order(orderId, accountId, orderStatus, 0L, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Order of(Long orderId, Long accountId, OrderStatus orderStatus, Long totalPrice) {
        return new Order(orderId, accountId, orderStatus, totalPrice, LocalDateTime.now(), LocalDateTime.now());

    }


    public void assignId(long id) {
        this.id = id;
    }

    public void updateStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
