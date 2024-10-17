package com.hhplus.commerce.domain.account.dto;

import com.hhplus.commerce.domain.account.entity.Account;

public record AccountResponse(Long id, Long userId, Long balance) {
    public AccountResponse(Account account) {
        this(account.getId(), account.getUserId(), account.getBalance());
    }

    public static AccountResponse of(Long id, Long userId, Long balance) {
        return new AccountResponse(id, userId, balance);
    }
}
