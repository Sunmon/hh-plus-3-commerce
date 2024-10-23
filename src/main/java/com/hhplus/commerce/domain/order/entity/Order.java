package com.hhplus.commerce.domain.order.entity;

import com.hhplus.commerce.domain.account.entity.Account;
import com.hhplus.commerce.domain.order.model.OrderStatus;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    private Long totalPrice;
    private OrderStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public static Order of(Account account, Long totalPrice) {
        return Order.of(null, account, totalPrice, OrderStatus.PENDING);
    }

    public static Order of(Long orderId, Account account, Long totalPrice) {
        return Order.of(orderId, account, totalPrice, OrderStatus.PENDING);
    }

    public static Order of(Long orderId, Account account, Long totalPrice, OrderStatus orderStatus) {
        return new Order(orderId, account, totalPrice, orderStatus, LocalDateTime.now(), LocalDateTime.now());
    }


    public void assignId(long id) {
        this.id = id;
    }

    public void updateStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
