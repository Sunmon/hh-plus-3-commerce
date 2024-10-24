package com.hhplus.commerce.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CommerceException extends RuntimeException {
    private final ErrorCode errorCode;
    private ErrorInfo errorInfo;

}
