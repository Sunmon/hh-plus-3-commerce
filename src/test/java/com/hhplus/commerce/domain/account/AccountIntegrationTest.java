package com.hhplus.commerce.domain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.commerce.domain.account.dto.AccountDepositRequest;
import com.hhplus.commerce.domain.account.dto.AccountResponse;
import com.hhplus.commerce.domain.account.entity.Account;
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
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHistoryService accountHistoryService;

    @BeforeEach
    public void setup() {
        Account account = Account.of(1L, 10000L);
        accountRepository.save(account);
    }

    @DisplayName("계좌 조회 테스트")
    @Test
    public void 계좌정보를_조회한다() throws Exception {
        // Given
        long accountId = 1L;
        long balance = 10000L;

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/accounts/" + accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        AccountResponse accountResponse = objectMapper.readValue(response.getContentAsString(), AccountResponse.class);
        assertThat(accountResponse.id()).isEqualTo(accountId);
        assertThat(accountResponse.balance()).isEqualTo(balance);
    }


    @ParameterizedTest
    @ValueSource(longs = {-500L, -1000L})
    @NullSource
    public void 유효하지_않은_파라미터로_요청시_오류를_반환한다(Long amount) throws Exception {
        // Given
        Long accountId = 1L;

        AccountDepositRequest request = new AccountDepositRequest(accountId, amount);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void 존재하지_않는_계좌에_접근시_오류를_반환한다() throws Exception {
        // Given
        long accountId = 123L;
        long amount = 1000L;

        AccountDepositRequest request = new AccountDepositRequest(accountId, amount);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("잔액 충전 테스트")
    @Test
    public void 계좌정보와_금액을_받아_잔액을_충전하고_결과를_리턴한다() throws Exception {
        // Given
        Long accountId = 1L;
        Long amount = 1000L;
        Long initBalance = 10000L;

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
        assertThat(accountResponse.balance()).isGreaterThanOrEqualTo(initBalance + amount);
//        assertThat(accountHistoryService.getHistories(accountId).get(0)).type.isEqualTo(AccountType.DEPOSIT);
//        assertThat(accountHistoryService.getHistories(accountId).get(0)).success.isEqualTo(true);
    }


    /**
     * 이 아래는 동시성 테스트 *********************************************************
     */

    @DisplayName("동시에 여러건을 충전해도 유실되지 않아야 한다")
    @Test
    public void 동시에_여러건을_충전해도_잔액이_맞아야_한다() throws Exception {
//        TODO
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

    @Test
    public void 동시에_여러건을_사용해도_잔액이_맞아야_한다() {

    }
}


