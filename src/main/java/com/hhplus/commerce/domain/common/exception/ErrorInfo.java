package com.hhplus.commerce.domain.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ErrorInfo {
    private Map<String, Object> details = new HashMap<>();

    // 빌더 호출 메소드
    public static ErrorInfoBuilder builder() {
        return new ErrorInfoBuilder();
    }

    public ErrorInfo addDetail(String key, Object value) {
        details.put(key, value);
        return this;
    }

    public Object getDetail(String key) {
        return details.get(key);
    }

    // 빌더 클래스
    public static class ErrorInfoBuilder {
        private final Map<String, Object> details = new HashMap<>();

        // 빌더 메소드로 필드 추가
        public ErrorInfoBuilder addDetail(String key, Object value) {
            details.put(key, value);
            return this;
        }

        // 빌드 메소드
        public ErrorInfo build() {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.details.putAll(details);
            return errorInfo;
        }
    }
}
