package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
class AccountRepositoryMemoryImpl implements AccountRepository {
    private final AtomicLong atomicId = new AtomicLong(1L);
    private final Map<Long, Account> db = new HashMap<>();

    @Override
    public Optional<Account> findById(Long accountId) {
        return Optional.of(new Account(accountId, 0L, 123L));
    }

    @Override
    public Account findByIdOrThrow(Long accountId) throws IllegalArgumentException {
        if (!db.containsKey(accountId)) throw new IllegalArgumentException("ACCOUNT_NOT_FOUND");
        return db.get(accountId);
    }

    @Override
    public Account insert(Account account) {
        account.assignId(nextId());
        account.setBalance(0L);
        account.setUserId(123L);
        db.put(account.getId(), account);
        return account;
    }

    private long nextId() {
        return atomicId.getAndIncrement();
    }
}
