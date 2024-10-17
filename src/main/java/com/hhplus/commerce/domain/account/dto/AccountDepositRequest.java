package com.hhplus.commerce.domain.account.dto;

import jakarta.validation.constraints.NotNull;

public record AccountDepositRequest(
        @NotNull Long accountId,
        @NotNull Long amount
) {
}
