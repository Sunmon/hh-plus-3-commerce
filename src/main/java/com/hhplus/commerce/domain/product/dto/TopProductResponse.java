package com.hhplus.commerce.domain.product.dto;

import jakarta.validation.constraints.NotNull;

public record TopProductResponse(@NotNull Long productId,
                                 @NotNull String name,
                                 @NotNull Long price,
                                 @NotNull Long sellCount) {
}
