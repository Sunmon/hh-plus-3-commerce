package com.hhplus.commerce.domain.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.commerce.domain.product.dto.ProductStockResponse;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    static final String BASE_URL = "/api/v1/products";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;
    //    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void beforeEach() {
//        productRepository.insert(new Product());
        Product product = Product.of(1L, "상품1", 100L);
        stockRepository.insert(Stock.of(1L, product, 100L));
    }


    @Test
    public void API_잘못된_파라미터_테스트() throws Exception {
        // Given
        Long productId = 0L;

        // When
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URL + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }


    @DisplayName("상품 정보 조회 테스트")
    @Test
    public void testGetProductInfo() throws Exception {
        // Given
        Long productId = 1L;
        assertThat(productId).isEqualTo(1L);

//        assertThat(productId).isEqualTo(1L);

        // When
//        ProductREquest
//        AccountRequest request = AccountRequest.of(accountId);
//        Product
        // When
        MockHttpServletResponse response = mockMvc.perform(get(BASE_URL + "/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
//                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        ProductStockResponse productStockResponse = objectMapper.readValue(response.getContentAsString(), ProductStockResponse.class);
        assertThat(productStockResponse.productId()).isGreaterThan(0L);
        assertThat(productStockResponse.stock()).isGreaterThanOrEqualTo(0);

    }
}
