package com.hhplus.commerce.domain.cart.model;

import com.hhplus.commerce.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CartErrorCode implements ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
