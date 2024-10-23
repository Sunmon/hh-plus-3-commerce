package com.hhplus.commerce.domain.stock.entity;

import com.hhplus.commerce.domain.order.model.OrderStatus;
import com.hhplus.commerce.domain.stock.model.StockStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * 재고 이력 엔티티
 * 재고의 추가/감소 이력을 관리한다.
 * <p>
 * 고민
 * - 같은 orderId를 공유하더라도 재고 이력은 (성공 실패 이력은) 각각 남아야 하는가? => 각각 관리해야 주문 내 어떤 상품이 재고가 부족했는지 확인할 수 있음
 * - 재고의 추가/감소가 실패하더라도 이력은 남아야 하는가? => 예
 * - 결제가 실패하여 감소한 재고를 다시 복구해야 하는 경우도 롤백한다고 로그를 남겨야 하는가? => 예
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
public class StockHistory {
    @Id
    private Long id;
    // 빠른 개발을 위해 연관관계를 맺지 않음.
    private Long stockId;

    // 재고를 추가하는경우 orderId가 없을 수 있음
    private Long orderId;

    // Product의 값이 변경되더라도 과거의 이력은 변경되면 안 되므로 반정규화하여 저장.
    private Long productId;
    private Long unitPrice; // 개별 상품 가격
    private Long quantity;
    private StockStatus stockStatus;
    private Boolean success; // 개별 상품의 성공 여부
    private OrderStatus orderSuccess; // 같은 주문 전체가 성공했는지 여부

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static StockHistory of(Long id, Long stockId, Long orderId, Long productId, Long unitPrice, Long quantity, StockStatus stockStatus, Boolean success, OrderStatus orderStatus) {
        return new StockHistory(id, stockId, orderId, productId, unitPrice, quantity, stockStatus, success, orderStatus, LocalDateTime.now(), LocalDateTime.now());
    }

    public static StockHistory of(Long stockId, Long orderId, Long productId, Long unitPrice, Long quantity, StockStatus stockStatus, Boolean success, OrderStatus orderStatus) {
        return new StockHistory(null, stockId, orderId, productId, unitPrice, quantity, stockStatus, success, orderStatus, LocalDateTime.now(), LocalDateTime.now());
    }

    public StockHistory assignId(Long id) {
        this.id = id;
        return this;
    }
}
