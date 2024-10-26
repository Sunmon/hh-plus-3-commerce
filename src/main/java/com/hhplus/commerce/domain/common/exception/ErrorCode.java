package com.hhplus.commerce.domain.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name(); // enum에 기본적으로 존재하는 메소드

    HttpStatus getHttpStatus();

    String getMessage();
}
