package com.hhplus.commerce.domain.stock.model;

import com.hhplus.commerce.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StockErrorCode implements ErrorCode {
    STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "Stock not found"),
    NOT_ENOUGH_STOCK(HttpStatus.CONFLICT, "Not enough stock"),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
