package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final OrderItemRepositoryMemoryImpl orderItemRepositoryMemory;
    private final OrderItemJpaRepository orderItemJpaRepository;

    @Override
    public Optional<OrderItem> findById(Long orderItemId) {
        return orderItemJpaRepository.findById(orderItemId);
    }

    @Override
    public List<OrderItem> findByProductId(Long productId) {
        return orderItemJpaRepository.findAllByProduct_Id(productId);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemJpaRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> saveAll(List<OrderItem> orderItems) {
        return orderItemJpaRepository.saveAll(orderItems);
    }

    @Override
    public List<OrderItem> findAllByOrderId(Long orderId) {
        return orderItemJpaRepository.findAllByOrder_Id(orderId);
    }

    @Override
    public List<OrderItem> findAllByProductId(Long productId) {
        return orderItemJpaRepository.findAllByProduct_Id(productId);
    }
}
