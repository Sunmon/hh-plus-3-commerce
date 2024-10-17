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
@Table(name = "order_items")
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
    private Long totalPrice; // 개당 가격 * 갯수

//    public static OrderItem of(Long id, Long quantity) {
//        return new OrderItem(id, null, null, quantity, 0L, 0L);
//    }


    public static OrderItem of(Long productId, Long quantity) {
        return new OrderItem(null, null, Product.of(productId), quantity, 0L, 0L);
    }

    public static OrderItem of(Long id, Long productId, Long quantity) {
        return new OrderItem(id, null, Product.of(productId), quantity, 0L, 0L);
    }

    public static OrderItem of(Product product, Long quantity) {
        return new OrderItem(null, null, product, quantity, product.getPrice(), product.getPrice() * quantity);
    }

    public static OrderItem of(Order order, Product product, Long quantity) {
        return new OrderItem(null, order, product, quantity, product.getPrice(), product.getPrice() * quantity);

    }

    public OrderItem assignId(Long id) {
        this.id = id;
        return this;
    }
}
