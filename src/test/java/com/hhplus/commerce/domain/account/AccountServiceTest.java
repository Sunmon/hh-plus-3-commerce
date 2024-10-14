package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AccountServiceTest {
    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void beforeEach() {
        accountRepository = new AccountRepositoryMemoryImpl();
        accountService = new AccountServiceImpl(accountRepository);
    }

    @DisplayName("계좌 IO로 정보 조회")
    @Test
    void getAccountInfo() {
        //given
        Long accountId = 1L;
        accountRepository.insert(new Account(accountId));
        // when
        Account account = accountService.getAccountInfo(accountId);
        // then
        assertThat(account.getId()).isEqualTo(accountId);
    }

    @DisplayName("존재하지 않는 계좌 ID로 조회 시도시 오류를 발생시켜야 한다.")
    @Test
    void getWrongAccountInfo() {
        //given
        Long accountId = 45678L;
        // when
        // then
        assertThatThrownBy(() -> accountService.getAccountInfo(accountId))
                .isInstanceOf(IllegalArgumentException.class);
    }


}
