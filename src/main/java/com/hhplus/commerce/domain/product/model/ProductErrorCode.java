package com.hhplus.commerce.domain.product.model;

import com.hhplus.commerce.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found"),
    PRODUCT_NOT_FOR_SALE(HttpStatus.CONFLICT, "Product is not for sale"),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
