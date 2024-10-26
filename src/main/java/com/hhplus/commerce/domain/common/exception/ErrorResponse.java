package com.hhplus.commerce.domain.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(description = "에러 응답")
public class ErrorResponse {
    private final String errorCode;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object details;
}
