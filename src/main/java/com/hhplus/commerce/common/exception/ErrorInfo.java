package com.hhplus.commerce.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ErrorInfo {
    private Map<String, Object> details = new HashMap<>();

    public ErrorInfo addDetail(String key, Object value) {
        details.put(key, value);
        return this;
    }

    public Object getDetail(String key) {
        return details.get(key);
    }
}
