package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long> {
//    List<OrderItem> findAllByOrderId(Long orderId);

    //    OrderItem findByProductIdOrElseThrow(Long productId) throws IllegalArgumentException;
//    OrderItem findByProductId(Long productId) throws IllegalArgumentException;

}
