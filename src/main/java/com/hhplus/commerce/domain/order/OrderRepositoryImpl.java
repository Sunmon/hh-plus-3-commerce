package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.common.exception.CustomException;
import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.model.OrderErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    OrderJpaRepository orderJpaRepository;
    OrderRepositoryMemoryImpl orderRepositoryMemoryImpl;

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderJpaRepository.findById(orderId);
    }

    @Override
    public Order findByIdOrElseThrow(Long orderId) throws IllegalArgumentException {
        return orderJpaRepository.findById(orderId).orElseThrow(() -> new CustomException(OrderErrorCode.ALREADY_ORDERED));
    }

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }
}
