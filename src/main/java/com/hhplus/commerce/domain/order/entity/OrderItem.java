package com.hhplus.commerce.domain.order.entity;

import com.hhplus.commerce.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class OrderItem {
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
//    private Long orderId;
    private Order Order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
//    private Long productId;
//    private String productName;
    private Product product;

    private Long quantity;
    private Long price;
    private Long totalPrice;


    public void assignId(Long id) {
        this.id = id;
    }
}
