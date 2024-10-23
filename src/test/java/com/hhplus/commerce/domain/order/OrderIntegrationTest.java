package com.hhplus.commerce.domain.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.commerce.domain.account.entity.Account;
import com.hhplus.commerce.domain.order.dto.OrderItemRequest;
import com.hhplus.commerce.domain.order.dto.OrderRequest;
import com.hhplus.commerce.domain.order.dto.OrderResponse;
import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.model.OrderStatus;
import com.hhplus.commerce.domain.product.ProductRepository;
import com.hhplus.commerce.domain.product.entity.Product;
import com.hhplus.commerce.domain.stock.StockRepository;
import com.hhplus.commerce.domain.stock.entity.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderIntegrationTest {
    static final String BASE_URL = "/api/v1/orders";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;


    @BeforeEach
    public void setUp() {
//        orderRepository.deleteAll();
//        orderItemRepository.deleteAll();
        productRepository.insert(Product.of(1L, "상품1", 1000L));
        productRepository.insert(Product.of(2L, "상품2", 2000L));
        productRepository.insert(Product.of(3L, "상품3", 3000L));

        List<Product> products = List.of(
                Product.of(1L, "상품1", 10L),
                Product.of(2L, "상품2", 10L),
                Product.of(3L, "상품3", 10L)
        );
        stockRepository.insert(Stock.of(1L, products.get(0), 100L));
        stockRepository.insert(Stock.of(2L, products.get(1), 200L));
        stockRepository.insert(Stock.of(3L, products.get(2), 300L));
    }


    @DisplayName("주문 정보 조회 테스트")
    @Test
    public void testGetOrderInfo() throws Exception {
        // Given
        Long orderId = 1L;
        Account account = Account.of(1L, 1L, 10000L);

        orderRepository.insert(Order.of(orderId, account, 100L, OrderStatus.PENDING));

        // When
//        ProductREquest
//        AccountRequest request = AccountRequest.of(accountId);
//        Product

        // When
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URL + "/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON))
//                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        OrderResponse orderResponse = objectMapper.readValue(response.getContentAsString(), OrderResponse.class);
        assertThat(orderResponse.orderId()).isGreaterThan(0L);
        assertThat(orderResponse.status()).isEqualTo(OrderStatus.PENDING);
    }


    @DisplayName("구매 테스트")
    @Test
    public void testPostOrder() throws Exception {
        // Given
//        Long orderId = 1L;
        Long accountId = 1L;

        List<Product> products = List.of(
                Product.of(1L, "상품1", 10L),
                Product.of(2L, "상품2", 10L),
                Product.of(3L, "상품3", 10L)
        );

        productRepository.saveAll(products);

        List<OrderItemRequest> orderItemRequests = List.of(
                OrderItemRequest.of(1L, 10L),
                OrderItemRequest.of(2L, 20L)
        );

        // When
//        List<OrderItem> orderItems = orderItemRequests.stream().map(item -> OrderItem.of(item.productId(), item.quantity())).toList();
//        orderItemRepository.saveAll(orderItems);
//        orderRepository.insert()
        OrderRequest request = OrderRequest.of(accountId, orderItemRequests);

//        ProductREquest
//        AccountRequest request = AccountRequest.of(accountId);
//        Product

        // When
        MockHttpServletResponse response = mockMvc.perform(post(BASE_URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        OrderResponse orderResponse = objectMapper.readValue(response.getContentAsString(), OrderResponse.class);
        assertThat(orderResponse.orderId()).isGreaterThan(0L);
        assertThat(orderResponse.status()).isEqualTo(OrderStatus.SUCCESS);
    }
}
