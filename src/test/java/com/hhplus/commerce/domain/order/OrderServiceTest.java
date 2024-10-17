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
//        orderRepository.insert(order);
////        OrderItem orderItem = new OrderItem(1L, 1L, 1L, "상품1", 10L, 100L, 10000L);
//        OrderItem orderItem = new OrderItem(1L, order, product, 10L, 100L, 10000L);
//        orderItemRepository.insert(orderItem);
    }

    @DisplayName("주문 ID로 정보 조회 테스트")
    @Test
    void testGetOrderInfo() {
        //given
        Long orderId = 1L;
        Order order = Order.of(1L, null, OrderStatus.PENDING);
        Product product = new Product();
        orderRepository.insert(order);
//        OrderItem orderItem = new OrderItem(1L, 1L, 1L, "상품1", 10L, 100L, 10000L);
        OrderItem orderItem = new OrderItem(1L, order, product, 10L, 100L, 10000L);
        orderItemRepository.insert(orderItem);
        // when
//        Map<Order, OrderItems> orderDetail = orderService.getOrder(orderId);
        Order orderDetail = orderService.getOrder(orderId);
//        Order resultOrder = orderDetail.keySet().stream().findFirst().get();
//        OrderItems resultOrderItems = orderDetail.get(resultOrder);
        // then
        assertThat(orderDetail.getId()).isEqualTo(orderId);
        assertThat(orderDetail.getStatus()).isEqualTo(OrderStatus.PENDING);
//        assertThat(resultOrderItems.getOrderItems().get(0).getOrder().getId()).isEqualTo(1L);
//        assertThat(resultOrderItems.getOrderItems().get(0).getQuantity()).isEqualTo(10L);
    }

    @DisplayName("존재하지 않는 주문 ID로 조회 시도시 오류를 발생시켜야 한다.")
    @Test
    void testGetWrongOrderInfo() {
        //given
        Long orderId = 45678L;
        // when
        // then
        assertThatThrownBy(() -> orderService.getOrder(orderId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 정보를 생성한다")
    @Test
    void testCreateOrder() {
        //given
        Long orderId = 1L;
        Long accountId = 1L;

//        List<OrderItemRequest> orderItemList = List.of(
//                OrderItemRequest.of(1L, 10L),
//                OrderItemRequest.of(2L, 20L)
//        );

//        List<OrderItem> orderItemList = List.of(
//                OrderItem.of(1L, 10L),
//                OrderItem.of(2L, 20L)
//        );
        // when
//        Map<Order, OrderItems> orderMap = orderService.order(accountId, new OrderItems(orderItemList));
//        Map<Order, OrderItems> orderMap = orderService.order(accountId, orderItemList);
//        Order order = orderMap.keySet().stream().findFirst().get();
//        OrderItems orderItems = orderMap.get(order);

        Order order = orderService.createOrder(accountId);

        // then
        assertThat(order.getId()).isEqualTo(orderId);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
//        assertThat(orderItems.getOrderItems().size()).isEqualTo(2);
    }


}

