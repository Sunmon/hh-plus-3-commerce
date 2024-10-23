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
    private Order Order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Long quantity;
    private Long price; // price의 가격이 변경되더라도 주문된 상품의 가격은 변하지 않아야 하기 때문에 price 컬럼을 따로 둠
    private Long totalPrice; // 개당 가격 * 갯수 (할인 기능 추가할까봐 컬럼 따로 빼둠)


    public static OrderItem of(Order order, Product product, Long quantity) {
        return OrderItem.of(order, product, quantity, product.getPrice() * quantity, product.getPrice() * quantity);
    }

    public static OrderItem of(Order order, Product product, Long quantity, Long price, Long totalPrice) {
        return new OrderItem(null, order, product, quantity, price, totalPrice);
    }

    public static OrderItem of(Product product, Long quantity) {
        return OrderItem.of(null, product, quantity);
    }

    public OrderItem assignId(Long id) {
        this.id = id;
        return this;
    }
}
