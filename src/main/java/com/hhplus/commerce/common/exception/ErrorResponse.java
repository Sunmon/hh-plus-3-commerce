package com.hhplus.commerce.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "에러 응답")
public record ErrorResponse(String errorCode, String message, String detail) {
}
