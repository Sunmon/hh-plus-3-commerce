package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.domain.account.AccountRepository;
import com.hhplus.commerce.domain.account.AccountRepositoryMemoryImpl;
import com.hhplus.commerce.domain.account.AccountService;
import com.hhplus.commerce.domain.account.AccountServiceImpl;
import com.hhplus.commerce.domain.account.entity.Account;
import com.hhplus.commerce.domain.order.dto.OrderItemRequest;
import com.hhplus.commerce.domain.order.dto.OrderItems;
import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.product.ProductRepository;
import com.hhplus.commerce.domain.product.ProductRepositoryMemoryImpl;
import com.hhplus.commerce.domain.product.entity.Product;
import com.hhplus.commerce.domain.stock.StockRepository;
import com.hhplus.commerce.domain.stock.StockRepositoryMemoryImpl;
import com.hhplus.commerce.domain.stock.StockService;
import com.hhplus.commerce.domain.stock.StockServiceImpl;
import com.hhplus.commerce.domain.stock.entity.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OrderProcessServiceTest {

    OrderProcessService orderProcessService;
    ProductRepository productRepository;
    StockRepository stockRepository;
    AccountRepository accountRepository;

    OrderRepository orderRepository;

    OrderService orderService;
    OrderItemService orderItemService;
    StockService stockService;
    AccountService accountService;
    OrderItemRepository orderItemRepository;

    @BeforeEach
    void beforeEach() {
        productRepository = new ProductRepositoryMemoryImpl();
        stockRepository = new StockRepositoryMemoryImpl();
        accountRepository = new AccountRepositoryMemoryImpl();
        orderRepository = new OrderRepositoryMemoryImpl();
        orderItemRepository = new OrderItemRepositoryMemoryImpl();

        orderService = new OrderServiceImpl(orderRepository);
//        orderItemService = new OrderItemServiceImpl(orderItemRepository);
        stockService = new StockServiceImpl(stockRepository);
        accountService = new AccountServiceImpl(accountRepository);

        orderProcessService = new OrderProcessServiceImpl(orderService, orderItemService, stockService, accountService);

    }


    @DisplayName("구매 요청이 들어오면 주문 업데이트 및 결제를 실행한다")
    @Test
    void testProcessOrder() {
        //given
        Long accountId = 1L;
        Long productId = 1L;
        Long stock = 100L;
        Product product1 = Product.of(productId, "상품1", 100L);
        Product product2 = Product.of(productId + 1, "상품2", 200L);
        accountRepository.insert(new Account(accountId, 10000L, 1L));
        productRepository.insert(product1);
        productRepository.insert(product2);
        stockRepository.insert(Stock.of(null, product1, stock));
        stockRepository.insert(Stock.of(null, product2, stock));

        List<OrderItemRequest> orderItemRequestList = List.of(
                new OrderItemRequest(productId, 10L),
                new OrderItemRequest(productId + 1, 10L)
        );


        //when
        Map<Order, OrderItems> orderOrderItemsMap = orderProcessService.processOrder(accountId, orderItemRequestList);

        //then
        Order order = orderOrderItemsMap.keySet().stream().findFirst().get();
        OrderItems orderItems = orderOrderItemsMap.get(order);
        assertThat(orderItems.getTotalPrice()).isEqualTo(1000L);
        assertThat(orderItems.getOrderItems().size()).isEqualTo(1);
        assertThat(orderItems.getOrderItems().get(0).getQuantity()).isEqualTo(10L);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.SUCCESS);

        assertThat(accountRepository.findByIdOrElseThrow(accountId).getBalance()).isEqualTo(8000L);

        assertThat(stockRepository.findByProductIdOrElseThrow(productId).getStock()).isEqualTo(100L - 10L);
    }


}