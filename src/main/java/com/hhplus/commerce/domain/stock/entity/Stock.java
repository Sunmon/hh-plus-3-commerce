package com.hhplus.commerce.domain.stock.entity;

import com.hhplus.commerce.domain.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "stocks")
public class Stock {
    @Id
    private Long id;

    //    @ManyToOne(fetch = FetchType.LAZY)
    // 굳이 manyToOne으로 개발 범위를 넓힐 필요 없을 것 같아 oneTOOne으로 변경
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private Long stock;
    private Boolean inStock;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Stock of(Product product, Long stock) {
        return Stock.of(null, product, stock);
    }

    public static Stock of(Long id, Product product, Long stock) {
        return new Stock(id, product, stock, true, LocalDateTime.now());
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public void decrease(Long quantity) {
        if (stock - quantity < 0) {
            throw new IllegalArgumentException("NOT_ENOUGH_STOCK");
        }
        stock -= quantity;
    }

    public boolean isInStock() {
        return inStock;
    }
}
