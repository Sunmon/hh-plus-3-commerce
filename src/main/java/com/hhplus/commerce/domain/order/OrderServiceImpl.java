package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) throws IllegalArgumentException {
        return orderRepository.findByIdOrThrow(orderId);
    }
}
