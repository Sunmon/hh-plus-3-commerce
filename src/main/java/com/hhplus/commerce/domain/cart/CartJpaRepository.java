package com.hhplus.commerce.domain.cart;

import com.hhplus.commerce.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserId(Long userId);

    Boolean deleteAllByUserId(Long userId);
}