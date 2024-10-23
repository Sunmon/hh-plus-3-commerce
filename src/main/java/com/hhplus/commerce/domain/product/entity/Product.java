package com.hhplus.commerce.domain.product.entity;

import com.hhplus.commerce.domain.product.model.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    // fk로 묶여있어서 Product의 데이터를 함부로 삭제할 수 없음. 따라서 status 값 추가.
    private ProductStatus status;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static Product of(String name, Long price) {
        return Product.of(null, name, price);
    }

    public static Product of(Long id, String name, Long price) {
        return new Product(id, name, price, ProductStatus.SELLING, LocalDateTime.now(), LocalDateTime.now());
    }

    public Product assignId(Long id) {
        this.id = id;
        return this;
    }
}
