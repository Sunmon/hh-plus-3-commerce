package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
//    Order findByIdOrElseThrow(Long orderId) throws IllegalArgumentException;

//    Optional<Order> findById(Long orderId);

}