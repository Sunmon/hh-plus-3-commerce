package com.hhplus.commerce.domain.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.commerce.domain.product.dto.ProductResponse;
import com.hhplus.commerce.domain.product.entity.Product;
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
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void beforeEach() {
        productRepository.insert(new Product());

    }


    @DisplayName("상품 정보 조회 테스트")
    @Test
    public void testGetProductInfo() throws Exception {
        // Given
        Long productId = 1L;

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
        ProductResponse productResponse = objectMapper.readValue(response.getContentAsString(), ProductResponse.class);
        assertThat(productResponse.productId()).isGreaterThan(0L);
        assertThat(productResponse.stock()).isGreaterThanOrEqualTo(0);

    }
}