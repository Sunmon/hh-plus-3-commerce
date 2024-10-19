package com.hhplus.commerce.domain.account.dto;

public record AccountRequest(Long id) {
    public static AccountRequest of(Long id) {
        return new AccountRequest(id);
    }
}
