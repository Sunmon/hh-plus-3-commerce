package com.hhplus.commerce.domain.product;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.commerce.domain.order.model.OrderStatus;
import com.hhplus.commerce.domain.product.dto.ProductStockResponse;
import com.hhplus.commerce.domain.product.dto.TopProductResponse;
import com.hhplus.commerce.domain.product.entity.Product;
import com.hhplus.commerce.domain.stock.StockHistoryRepository;
import com.hhplus.commerce.domain.stock.StockRepository;
import com.hhplus.commerce.domain.stock.StockService;
import com.hhplus.commerce.domain.stock.entity.Stock;
import com.hhplus.commerce.domain.stock.entity.StockHistory;
import com.hhplus.commerce.domain.stock.model.StockStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductIntegrationTest {

    static final String BASE_URL = "/api/v1/products";


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가. 한글 사용 위함.
                .build();

        Product macbook = Product.of("맥북", 1_000_000L);
        Product airpods = Product.of("에어팟", 30_000L);
        Product note = Product.of("노트", 5000L);
        Product pen = Product.of("펜", 500L);
        Product mouse = Product.of("마우스", 1000L);
        Product keyboard = Product.of("키보드", 1500L);
        Product iphone = Product.of("아이폰", 100_000L);

        productRepository.save(macbook);
        productRepository.save(airpods);
        productRepository.save(note);
        productRepository.save(pen);
        productRepository.save(mouse);
        productRepository.save(keyboard);
        productRepository.save(iphone);

        stockRepository.save(Stock.of(macbook, 1L));
        stockRepository.save(Stock.of(airpods, 5L));
        stockRepository.save(Stock.of(note, 50L));
        stockRepository.save(Stock.of(pen, 100L));
        stockRepository.save(Stock.of(mouse, 500L));
        stockRepository.save(Stock.of(keyboard, 1000L));
        stockRepository.save(Stock.of(iphone, 1200L));


        stockHistoryRepository.save(StockHistory.of(1L, null, 1L, 1_000_000L, 1L, StockStatus.SUB, true, OrderStatus.ORDER_SUCCESS));
        stockHistoryRepository.save(StockHistory.of(2L, null, 2L, 30_000L, 2L, StockStatus.SUB, true, OrderStatus.ORDER_SUCCESS));
        stockHistoryRepository.save(StockHistory.of(3L, null, 3L, 5000L, 3L, StockStatus.SUB, true, OrderStatus.ORDER_SUCCESS));
        stockHistoryRepository.save(StockHistory.of(4L, null, 4L, 500L, 5L, StockStatus.SUB, true, OrderStatus.ORDER_SUCCESS));
        stockHistoryRepository.save(StockHistory.of(5L, null, 5L, 1000L, 10L, StockStatus.SUB, true, OrderStatus.ORDER_SUCCESS));
        stockHistoryRepository.save(StockHistory.of(6L, null, 6L, 1500L, 100L, StockStatus.SUB, true, OrderStatus.ORDER_SUCCESS));
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L})
    @NullSource
    public void 유효하지_않은_파라미터로_요청시_오류를_반환한다(Long productId) throws Exception {
        // Given

        // When
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URL + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @DisplayName("상품 정보 조회 테스트")
    @Test
    public void 상품정보를_조회한다() throws Exception {
        // Given
        Long productId = 1L;

        MockHttpServletResponse response = mockMvc.perform(get(BASE_URL + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
//                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        ProductStockResponse productStockResponse = objectMapper.readValue(response.getContentAsString(), ProductStockResponse.class);
        assertThat(productStockResponse.productId()).isEqualTo(productId);
        assertThat(productStockResponse.name()).isEqualTo("맥북");
        assertThat(productStockResponse.stock()).isEqualTo(1);
    }


    @DisplayName("인기 상품 정보 조회 테스트")
    @Test
//    @ParameterizedTest
//    @CsvSource({"2024-01-01", "2024-12-12", "5"}, {"2024-01-01", "2024-12-12", "5"})
    public void 기간_N_M동안_가장_많이_팔린_K개_상품정보를_조회한다() throws Exception {

        // Given
        LocalDateTime from = LocalDateTime.parse("2024-01-01T08:00:00");
        LocalDateTime to = LocalDateTime.now(); //"2023-11-01T20:00:00";
        int size = 5;

        // Given
        String path = "/top?from=" + from.toString() + "&to=" + to.toString() + "&size=" + size;

        MockHttpServletResponse response = mockMvc.perform(get(BASE_URL + path)
                        .contentType(MediaType.APPLICATION_JSON))
//                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());


        List<TopProductResponse> topProductResponses = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<TopProductResponse>>() {
        });

        assertThat(topProductResponses.size()).isEqualTo(size);
        assertThat(topProductResponses.stream().mapToLong(TopProductResponse::sellCount)).isSortedAccordingTo(Comparator.reverseOrder());
    }
}
