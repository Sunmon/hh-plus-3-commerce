package com.hhplus.commerce.domain.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "resource not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed"),
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "Method argument not valid"),
    CONFLICT(HttpStatus.CONFLICT, "conflict request"),


    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid input value"),
    UNDEFINED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Undefined error"),
    ;

    private final HttpStatus httpStatus;
    private final String message;


}
