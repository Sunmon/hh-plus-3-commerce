package com.hhplus.commerce.domain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.commerce.domain.account.dto.AccountDepositRequest;
import com.hhplus.commerce.domain.account.dto.AccountResponse;
import com.hhplus.commerce.domain.account.entity.Account;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        accountRepository.insert(new Account());
    }


    @DisplayName("계좌 조회 테스트")
    @Test
    public void testGetAccount() throws Exception {
        // Given
        long accountId = 1L;
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

    @DisplayName("잔액 충전 테스트")
    @Test
    public void testDepositAccount() throws Exception {
        // Given
        Long accountId = 1L;
        Long amount = 1000L;

        AccountDepositRequest request = new AccountDepositRequest(accountId, amount);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        AccountResponse accountResponse = objectMapper.readValue(response.getContentAsString(), AccountResponse.class);
        assertThat(accountResponse.id()).isEqualTo(accountId);
        assertThat(accountResponse.balance()).isGreaterThanOrEqualTo(0L);
    }

    @DisplayName("유효하지 않은 금액으로 충전 테스트")
    @Test
    public void testDepositAccountWithWrongAmount() throws Exception {
        // Given
        long accountId = 123L;
        long amount = -1000L;

        AccountDepositRequest request = new AccountDepositRequest(accountId, amount);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        AccountResponse accountResponse = objectMapper.readValue(response.getContentAsString(), AccountResponse.class);
        assertThat(accountResponse.id()).isEqualTo(accountId);
        assertThat(accountResponse.balance()).isGreaterThanOrEqualTo(0L);
    }

    @DisplayName("동시에 여러건을 충전해도 유실되지 않아야 한다")
    @Test
    public void testDepositAccountAtSametime() throws Exception {
        // TODO 동시에 여러건을 충전해도 유실되지 않아야 한다
        //  Given
        long accountId = 123L;
        long amount = 1000L;

        AccountDepositRequest request = new AccountDepositRequest(accountId, amount);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();


        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        AccountResponse accountResponse = objectMapper.readValue(response.getContentAsString(), AccountResponse.class);
        assertThat(accountResponse.id()).isEqualTo(accountId);
        assertThat(accountResponse.balance()).isGreaterThanOrEqualTo(10000L);
    }
}
