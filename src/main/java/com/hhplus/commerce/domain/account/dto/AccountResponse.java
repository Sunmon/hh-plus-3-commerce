package com.hhplus.commerce.domain.account.dto;

public record AccountResponse(Long id, Long balance) {
    public static AccountResponse of(Long id, Long balance) {
        return new AccountResponse(id, balance);
    }
}
