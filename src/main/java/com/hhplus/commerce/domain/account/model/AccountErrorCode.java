package com.hhplus.commerce.domain.account.model;

import com.hhplus.commerce.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AccountErrorCode implements ErrorCode {
    ACCOUNT_NOT_EXIST(HttpStatus.NOT_FOUND, "Account does not exist"),
    BALANCE_NOT_ENOUGH(HttpStatus.CONFLICT, "Balance is not enough"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
