package com.hhplus.commerce.domain.order;

public enum OrderStatus {
    PENDING, // 1. 주문 대기
    SUCCESS, // 2-1. 상품 주문 성공
    FAILED, // 2-2. 상품 주문 실패

    PAY_SUCCESS, // 3-1. 결제 완료
    PAY_FAILED, // 3-2. 결제 실패
    CANCELED, // 4. 주문 취소
}
