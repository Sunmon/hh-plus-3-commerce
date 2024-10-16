package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.entity.OrderItem;
import com.hhplus.commerce.domain.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderServiceTest {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private OrderService orderService;

    @BeforeEach
    void beforeEach() {
        orderRepository = new OrderRepositoryMemoryImpl();
        orderItemRepository = new OrderItemRepositoryMemoryImpl();
        orderService = new OrderServiceImpl(orderRepository);
        Order order = new Order();
        Product product = new Product();
        orderRepository.insert(order);
//        OrderItem orderItem = new OrderItem(1L, 1L, 1L, "상품1", 10L, 100L, 10000L);
        OrderItem orderItem = new OrderItem(1L, order, product, 10L, 100L, 10000L);
        orderItemRepository.insert(orderItem);
    }

    @DisplayName("주문 ID로 정보 조회 테스트")
    @Test
    void testGetOrderInfo() {
        //given
        Long orderId = 1L;
        // when
        Order order = orderService.getOrder(orderId);
        OrderItem orderItem = orderItemRepository.findById(1L).get();
        // then
        assertThat(order.getId()).isEqualTo(orderId);
        assertThat(orderItem.getId()).isEqualTo(1L);
        assertThat(orderItem.getOrder().getId()).isEqualTo(1L);
    }

    @DisplayName("존재하지 않는 주문 ID로 조회 시도시 오류를 발생시켜야 한다.")
    @Test
    void getWrongOrderInfo() {
        //given
        Long orderId = 45678L;
        // when
        // then
        assertThatThrownBy(() -> orderService.getOrder(orderId))
                .isInstanceOf(IllegalArgumentException.class);
    }

}

