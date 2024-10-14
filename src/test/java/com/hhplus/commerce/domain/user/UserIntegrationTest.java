package com.hhplus.commerce.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.commerce.domain.account.dto.AccountRequest;
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
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("유저의 계좌 조회 테스트")
    @Test
    public void testGetBalance() throws Exception {
        // Given
        Long userId = 1L;
        Long accountId = 123L;

        AccountRequest request = AccountRequest.of(accountId);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/users/" + userId + "/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        AccountResponse accountResponse = objectMapper.readValue(response.getContentAsString(), AccountResponse.class);
        assertThat(accountResponse.id()).isGreaterThan(0L);

    }
}
