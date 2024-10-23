package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.dto.OrderItemRequest;
import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.entity.OrderItem;
import com.hhplus.commerce.domain.order.model.OrderItems;
import com.hhplus.commerce.domain.product.ProductService;
import com.hhplus.commerce.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final ProductService productService;

//    @PersistenceContext
//    private EntityManager entityManager;

    @Override
    public OrderItem createOrderItem(Long productId, Long quantity) {
//        Product product = entityManager.getReference(Product.class, productId);
        Product product = productService.findById(productId);
        return OrderItem.of(product, quantity);
    }

    @Override
    public OrderItem createOrderItem(Long orderId, Long productId, Long quantity) {
//        Order order = entityManager.getReference(Order.class, orderId);
//        Order order = entityManager.getReference(Order.class, orderId);
//        Product product = entityManager.getReference(Product.class, productId);
        Product product = productService.findById(productId);
        Order order = orderService.getOrder(orderId);
//        return OrderItem.of(order, product, quantity);
        return createOrderItem(order, product, quantity);
    }

    @Override
    public OrderItem createOrderItem(Order order, Long productId, Long quantity) {
//        Product product = entityManager.getReference(Product.class, productId);
//        return OrderItem.of(order, product, quantity);
        Product product = productService.findById(productId);
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

    @Override
    @Transactional
    public OrderItems createOrderItems(Long orderId, List<OrderItemRequest> orderItemRequests) {
        List<OrderItem> orderItemList = orderItemRequests.stream().map(orderItemRequest -> {
            return createOrderItem(orderId, orderItemRequest.productId(), orderItemRequest.quantity());
        }).toList();
        orderItemList = orderItemRepository.saveAll(orderItemList);
        return new OrderItems(orderItemList);
    }

    @Override
    public OrderItems getOrderItemsByOrderId(Long orderId) {
        return new OrderItems(orderItemRepository.findAllByOrderId(orderId));
    }
}
