package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;
import org.springframework.stereotype.Service;

@Service
class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccountInfo(Long accountId) throws IllegalArgumentException {
        return accountRepository.findByIdOrThrow(accountId);
    }
}
