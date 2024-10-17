package com.hhplus.commerce.domain.order;

public enum OrderStatus {
    PENDING, // 주문 대기
    ORDER_SUCCESS, // 상품 주문 성공
    ORDER_FAILED, // 상품 주문 실패

    PAYMENT_SUCCESS, // 결제 완료
    PAYMENT_FAILED, // 결제 실패

    SUCCESS, // 최종 구매 성공
    FAILED // 최종 구매 실패
}
