package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;

import java.util.Optional;

//@Repository
public interface AccountRepository {
    Optional<Account> findById(Long accountId);

    Account findByIdOrThrow(Long accountId) throws IllegalArgumentException;

    Account insert(Account account);

    Account save(Account account);
}
