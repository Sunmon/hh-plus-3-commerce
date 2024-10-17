package com.hhplus.commerce.domain.stock.entity;

import com.hhplus.commerce.domain.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "stocks")
public class Stock {
    @Id
    private Long id;

    //    private Long productId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private Long stock;
    private Boolean inStock;


    public static Stock of(Long id, Long productId, Long stock) {
        return new Stock(id, Product.of(productId), stock, true);
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
}
