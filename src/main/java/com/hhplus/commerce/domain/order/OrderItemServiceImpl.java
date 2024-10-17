package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.entity.OrderItem;
import com.hhplus.commerce.domain.product.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderItem createOrderItem(Long productId, Long quantity) {
        Product product = entityManager.getReference(Product.class, productId);
        return OrderItem.of(product, quantity);
    }

    @Override
    public OrderItem createOrderItem(Long orderId, Long productId, Long quantity) {
//        Order order = entityManager.getReference(Order.class, orderId);
        Order order = entityManager.getReference(Order.class, orderId);
        Product product = entityManager.getReference(Product.class, productId);
//        return OrderItem.of(order, product, quantity);
        return createOrderItem(order, product, quantity);
    }

    @Override
    public OrderItem createOrderItem(Order order, Long productId, Long quantity) {
        Product product = entityManager.getReference(Product.class, productId);
//        return OrderItem.of(order, product, quantity);
        return createOrderItem(order, product, quantity);
    }

    @Override
    public OrderItem createOrderItem(Order order, Product product, Long quantity) {
        return OrderItem.of(order, product, quantity);
    }


    @Override
    public OrderItem findById(Long orderItemId) {
        return null;
    }

    @Override
    public OrderItem findByProductIdOrElseThrow(Long productId) throws IllegalArgumentException {
        return null;
    }
}
