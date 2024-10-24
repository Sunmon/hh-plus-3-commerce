package com.hhplus.commerce.domain.order.model;

import com.hhplus.commerce.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
    ORDER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Order failed"),
    STOCK_FAILURE(HttpStatus.CONFLICT, "Stock failure"),
    PAYMENT_FAILED(HttpStatus.CONFLICT, "Payment failed"),
    ALREADY_ORDERED(HttpStatus.CONFLICT, "Already ordered"),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
