package com.hhplus.commerce.domain.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.commerce.domain.order.dto.OrderResponse;
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
public class OrderIntegrationTest {
    static final String BASE_URL = "/api/v1/orders";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("주문 정보 조회 테스트")
    @Test
    public void testGetOrderInfo() throws Exception {
        // Given
        Long orderId = 1L;

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
}
