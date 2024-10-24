package com.hhplus.commerce.domain.account.dto;

import jakarta.validation.constraints.NotNull;

public record AccountRequest(@NotNull Long id) {

    public static AccountRequest of(@NotNull Long id) {
        return new AccountRequest(id);
    }
}
