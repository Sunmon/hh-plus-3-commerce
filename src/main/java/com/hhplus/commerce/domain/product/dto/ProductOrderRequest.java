package com.hhplus.commerce.domain.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductOrderRequest(@NotNull Long userId, @NotNull @PositiveOrZero Integer quantity) {
}
