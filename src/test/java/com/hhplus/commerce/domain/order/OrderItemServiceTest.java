package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.entity.OrderItem;
import com.hhplus.commerce.domain.product.ProductRepository;
import com.hhplus.commerce.domain.product.ProductRepositoryMemoryImpl;
import com.hhplus.commerce.domain.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderItemServiceTest {

    private ProductRepository productRepository;
    private OrderItemRepository orderItemRepository;
//    private OrderService orderService;

    private OrderItemService orderItemService;

    @BeforeEach
    void beforeEach() {
//        orderRepository = new OrderRepositoryMemoryImpl();
        productRepository = new ProductRepositoryMemoryImpl();
        orderItemRepository = new OrderItemRepositoryMemoryImpl();
        orderItemService = new OrderItemServiceImpl(orderItemRepository);

//        orderService = new OrderServiceImpl(orderRepository, orderItemRepository);
//        Order order = new Order();
        Product product = Product.of(1L, "상품1", 100L);
        productRepository.insert(product);
//        orderRepository.insert(order);
////        OrderItem orderItem = new OrderItem(1L, 1L, 1L, "상품1", 10L, 100L, 10000L);
//        OrderItem orderItem = new OrderItem(1L, order, product, 10L, 100L, 10000L);
//        orderItemRepository.insert(orderItem);
    }

    @DisplayName("productId로 주문 아이템 생성 테스트")
    @Test
    void testCreateOrderItem() {
        //given
        Long orderId = 1L;
//        Order order = new Order();
        Order order = Order.of(1L, null, OrderStatus.PENDING);
        Product product = Product.of(2L, "상품2", 200L);

        // when
        OrderItem orderItem = orderItemService.createOrderItem(order, product, 10L);
        // then
        assertThat(orderItem.getOrder().getId()).isEqualTo(orderId);
        assertThat(orderItem.getPrice()).isEqualTo(200L);
        assertThat(orderItem.getTotalPrice()).isEqualTo(10L * 200L);
    }

}
