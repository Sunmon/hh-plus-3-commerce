package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public
class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account getAccountInfo(Long accountId) throws IllegalArgumentException {
        return accountRepository.findByIdOrElseThrow(accountId);
    }

    @Override
    public Account deposit(Long accountId, Long amount) throws IllegalArgumentException {
//        Account account = accountRepository.findByIdOrElseThrow(accountId);
        Account account = getAccountInfo(accountId);
        return accountRepository.save(account.deposit(amount));
    }

    @Override
    public Account withdraw(Long accountId, Long amount) throws IllegalArgumentException {
        Account account = getAccountInfo(accountId);
        return accountRepository.save(account.withdraw(amount));
    }
}
