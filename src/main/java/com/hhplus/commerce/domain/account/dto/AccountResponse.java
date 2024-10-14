package com.hhplus.commerce.domain.account.dto;

import com.hhplus.commerce.domain.account.entity.Account;

public record AccountResponse(Long id, Long balance) {
    public AccountResponse(Account account) {
        this(account.getId(), account.getBalance());
    }

    public static AccountResponse of(Long id, Long balance) {
        return new AccountResponse(id, balance);
    }
}
