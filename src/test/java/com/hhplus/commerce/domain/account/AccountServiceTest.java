package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountServiceTest {
    private AccountService accountService;

    @BeforeEach
    void beforeEach() {
        accountService = new AccountServiceImpl();
    }


    @Test
    void getAccountInfo() {
        //given
        Long accountId = 1L;
//    AccountRequest requst;
        // when
        Account account = accountService.getAccountInfo(accountId);

        // then
        assertThat(account.getId()).isPositive();
    }

    private static class AccountServiceImpl implements AccountService {

        private final AccountRepository accountRepository;

        public AccountServiceImpl() {
            this.accountRepository = new AccountRepositoryMemoryImpl();
        }


        @Override
        public Account getAccountInfo(Long accountId) {
//            Account account = new Account(accountId);
            Account account = accountRepository.findById(accountId);
            return account;
        }
    }

    private static class AccountRepositoryMemoryImpl implements AccountRepository {

        private final AtomicLong atomicId = new AtomicLong(1L);

        @Override
        public Account findById(Long accountId) {
            return new Account(accountId, 0L);
        }

        public Account insert(Account account) {
            account.assignId(nextId());
            account.setBalance(0L);
            return account;
        }

        private long nextId() {
            return atomicId.getAndIncrement();
        }

    }
}
