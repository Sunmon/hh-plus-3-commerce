package com.hhplus.commerce.domain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.commerce.domain.account.dto.AccountResponse;
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
public class AccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("계좌 조회 테스트")
    @Test
    public void testGetAccount() throws Exception {
        // Given
        long accountId = 123L;
//        AccountRequest request = AccountRequest.of(accountId);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/accounts/" + accountId)
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
        ).andReturn().getResponse();
        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        AccountResponse accountResponse = objectMapper.readValue(response.getContentAsString(), AccountResponse.class);
        assertThat(accountResponse.id()).isEqualTo(accountId);
        assertThat(accountResponse.balance()).isGreaterThanOrEqualTo(0L);
    }
}
