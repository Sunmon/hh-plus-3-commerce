package com.hhplus.commerce.domain.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.commerce.domain.account.AccountRepository;
import com.hhplus.commerce.domain.account.AccountService;
import com.hhplus.commerce.domain.account.TransactionType;
import com.hhplus.commerce.domain.account.entity.Account;
import com.hhplus.commerce.domain.order.dto.OrderItemRequest;
import com.hhplus.commerce.domain.order.dto.OrderRequest;
import com.hhplus.commerce.domain.order.dto.OrderResponse;
import com.hhplus.commerce.domain.order.entity.Order;
import com.hhplus.commerce.domain.order.entity.OrderItem;
import com.hhplus.commerce.domain.order.model.OrderStatus;
import com.hhplus.commerce.domain.product.ProductRepository;
import com.hhplus.commerce.domain.product.ProductService;
import com.hhplus.commerce.domain.product.entity.Product;
import com.hhplus.commerce.domain.stock.StockRepository;
import com.hhplus.commerce.domain.stock.StockService;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderIntegrationTest {
    static final String BASE_URL = "/api/v1/orders";
    final long AIRPODS_PRICE = 30_000;
    final long AIRPODS_STOCK = 5;
    final long NOTE_PRICE = 5_000;
    final long NOTE_STOCK = 50;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private OrderItemService orderItemService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가. 한글 사용 위함.
                .build();


        accountRepository.save(Account.of(1L, 1L, 10000L));
//        accountRepository.save(Account.of(2L, 2L, 100_000L));
//        accountRepository.save(Account.of(3L, 3L, 2000L));

        Product macbook = Product.of(1L, "맥북", 1_000_000L);
        Product airpods = Product.of(2L, "에어팟", AIRPODS_PRICE);
        Product note = Product.of(3L, "노트", NOTE_PRICE);
        Product pen = Product.of(4L, "펜", 500L);
        Product mouse = Product.of(5L, "마우스", 1000L);
        Product keyboard = Product.of(6L, "키보드", 1500L);
        Product iphone = Product.of(7L, "아이폰", 100_000L);

        productRepository.save(macbook);
        productRepository.save(airpods);
        productRepository.save(note);
        productRepository.save(pen);
        productRepository.save(mouse);
        productRepository.save(keyboard);
        productRepository.save(iphone);

        stockRepository.save(Stock.of(macbook, 1L));
        stockRepository.save(Stock.of(airpods, AIRPODS_STOCK));
        stockRepository.save(Stock.of(note, NOTE_STOCK));
        stockRepository.save(Stock.of(pen, 100L));
        stockRepository.save(Stock.of(mouse, 500L));
        stockRepository.save(Stock.of(keyboard, 1000L));
        stockRepository.save(Stock.of(iphone, 1200L));


        Order order = Order.of(1L, Account.of(1L, 1L, 10000L), 10000L, OrderStatus.ORDER_SUCCESS);
        List<OrderItem> orderItems = List.of(
                OrderItem.of(order, pen, 10L),
                OrderItem.of(order, note, 1L)
        );
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
    }


    @Test
    public void 유효하지_않은_파라미터로_요청시_오류를_반환한다() throws Exception {
        // Given
        OrderRequest request = OrderRequest.of(null, null);

        // When
        MockHttpServletResponse response = mockMvc.perform(post(BASE_URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("주문 정보 조회 테스트")
    @Test
    public void 주문_정보를_조회한다() throws Exception {
        // Given
        Long orderId = 1L;
        // When
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URL + "/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        OrderResponse orderResponse = objectMapper.readValue(response.getContentAsString(), OrderResponse.class);
        assertThat(orderResponse.orderId()).isEqualTo(1L);
        assertThat(orderResponse.orderItems().size()).isEqualTo(2);
        assertThat(orderResponse.orderItems().stream().mapToLong(o -> o.getPrice() * o.getQuantity()).sum()).isEqualTo(10000L);
        assertThat(orderResponse.status()).isEqualTo(OrderStatus.ORDER_SUCCESS);
    }


    @DisplayName("구매 테스트")
    @Test
    public void 재고와_계좌금액이_충분하면_주문을_성공한다() throws Exception {
        // Given
        Long accountId = 123L;
        Long balance = 100_000L;
        Account account = Account.of(accountId, accountId, balance);
        accountRepository.save(account);

        OrderRequest request = OrderRequest.of(accountId, List.of(
                OrderItemRequest.of(2L, 2L), // 에어팟
                OrderItemRequest.of(3L, 2L) // 노트
        ));

        // When
        MockHttpServletResponse response = mockMvc.perform(post(BASE_URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        OrderResponse orderResponse = objectMapper.readValue(response.getContentAsString(), OrderResponse.class);
        assertThat(orderResponse.orderId()).isGreaterThan(0L);
        assertThat(orderResponse.status()).isEqualTo(OrderStatus.ORDER_SUCCESS);
        assertThat(orderResponse.totalPrice()).isEqualTo(70000L);

        // 주문 후 재고 확인
        assertThat(stockService.getStockByProductId(2L).getStock()).isEqualTo(5L - 2L);
        assertThat(stockService.getStockByProductId(3L).getStock()).isEqualTo(50L - 2L);

        // 주문 후 계좌 확인
        assertThat(accountService.getAccount(accountId).getBalance()).isEqualTo(balance - orderResponse.totalPrice());
        assertThat(accountService.getHistoriesById(accountId).get(0).getTransactionType()).isEqualTo(TransactionType.WITHDRAW);
        assertThat(accountService.getHistoriesById(accountId).get(0).getTransactionAmount()).isEqualTo(70000L);


        // 주문 후 주문 히스토리 확인
        assertThat(orderService.getOrder(orderResponse.orderId()).getTotalPrice()).isEqualTo(orderResponse.totalPrice());
        assertThat(orderItemService.getOrderItemsByOrderId(orderResponse.orderId()).size()).isEqualTo(2);
        assertThat(stockService.getHistoriesByOrderId(orderResponse.orderId()).size()).isEqualTo(2);
    }


    @DisplayName("구매 테스트")
    @Test
    public void 재고가_부족하면_주문을_실패한다() throws Exception {
        // Given
        Long accountId = 123L;
        Long balance = 100_000L;
        Long orderId = 1L;
        Account account = Account.of(accountId, accountId, balance);
        accountRepository.save(account);

        OrderRequest request = OrderRequest.of(accountId, List.of(
                OrderItemRequest.of(2L, 10L), // 에어팟
                OrderItemRequest.of(3L, 2L) // 노트
        ));

        // When
        MockHttpServletResponse response = mockMvc.perform(post(BASE_URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());

        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        // 주문 후 재고 확인
        assertThat(stockService.getStockByProductId(2L).getStock()).isEqualTo(5L);
        assertThat(stockService.getStockByProductId(3L).getStock()).isEqualTo(50L);
        // 주문 후 계좌 확인
        assertThat(accountService.getAccount(accountId).getBalance()).isEqualTo(balance);
        assertThat(accountService.getHistoriesById(accountId)).isEmpty();
        // 주문 후 주문 히스토리 확인
        assertThat(orderService.getOrder(orderId).getTotalPrice()).isEqualTo(AIRPODS_PRICE * 10L + NOTE_PRICE * 2L);
        assertThat(orderItemService.getOrderItemsByOrderId(orderId).size()).isEqualTo(2);
        assertThat(stockService.getHistoriesByOrderId(orderId).size()).isEqualTo(2);
        //TODO 주문 후 재고 히스토리 확인
        assertThat(stockService.getHistoriesByOrderId(orderId).size()).isEqualTo(2);
        assertThat(stockService.getHistoriesByOrderId(orderId).stream().filter(sh -> sh.getProductId() == 2L).findFirst().get().getOrderSuccess()).isEqualTo(OrderStatus.ORDER_FAILED);
        assertThat(stockService.getHistoriesByOrderId(orderId).stream().filter(sh -> sh.getProductId() == 3L).findFirst().get().getOrderSuccess()).isEqualTo(OrderStatus.ORDER_FAILED);
        assertThat(stockService.getHistoriesByOrderId(orderId).stream().filter(sh -> sh.getProductId() == 2L).findFirst().get().getSuccess()).isEqualTo(true);
        assertThat(stockService.getHistoriesByOrderId(orderId).stream().filter(sh -> sh.getProductId() == 3L).findFirst().get().getSuccess()).isEqualTo(false);
    }


    @DisplayName("구매 테스트")
    @Test
    public void 잔액이_부족하면_주문을_실패한다() throws Exception {
        // Given
        Long accountId = 123L;
        Long balance = 100L;
        Long orderId = 1L;
        Account account = Account.of(accountId, accountId, balance);
        accountRepository.save(account);

        OrderRequest request = OrderRequest.of(accountId, List.of(
                OrderItemRequest.of(3L, 2L), // 노트
                OrderItemRequest.of(2L, 10L) // 에어팟
        ));

        // When
        MockHttpServletResponse response = mockMvc.perform(post(BASE_URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);

        // 주문 후 재고 확인
        assertThat(stockService.getStockByProductId(2L).getStock()).isEqualTo(NOTE_STOCK);
        assertThat(stockService.getStockByProductId(3L).getStock()).isEqualTo(AIRPODS_STOCK);

        // 주문 후 계좌 확인
        assertThat(accountService.getAccount(accountId).getBalance()).isEqualTo(balance);
        assertThat(accountService.getHistoriesById(accountId).get(0).getTransactionType()).isEqualTo(TransactionType.WITHDRAW);
        assertThat(accountService.getHistoriesById(accountId).get(0).getTransactionAmount()).isEqualTo(70000L);

        assertThat(orderItemService.getOrderItemsByProductId(2L).size()).isEqualTo(1);
        assertThat(orderService.getOrderByAccountId(accountId).get(0).getStatus()).isEqualTo(OrderStatus.ORDER_FAILED);

        //TODO 주문 후 재고 히스토리 확인
        assertThat(stockService.getHistoriesByOrderId(orderId).size()).isEqualTo(2);
        assertThat(stockService.getHistoriesByOrderId(orderId).stream().filter(sh -> sh.getProductId() == 2L).findFirst().get().getOrderSuccess()).isEqualTo(OrderStatus.ORDER_FAILED);
        assertThat(stockService.getHistoriesByOrderId(orderId).stream().filter(sh -> sh.getProductId() == 3L).findFirst().get().getOrderSuccess()).isEqualTo(OrderStatus.ORDER_FAILED);
        assertThat(stockService.getHistoriesByOrderId(orderId).stream().filter(sh -> sh.getProductId() == 2L).findFirst().get().getSuccess()).isEqualTo(true);
        assertThat(stockService.getHistoriesByOrderId(orderId).stream().filter(sh -> sh.getProductId() == 3L).findFirst().get().getSuccess()).isEqualTo(false);

    }
}
