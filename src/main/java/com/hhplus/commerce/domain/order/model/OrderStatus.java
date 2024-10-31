package com.hhplus.commerce.domain.order.model;

public enum OrderStatus {
    PENDING, // 주문 대기
    PRODUCT_SUCCESS, // 상품 구매 성공
    PRODUCT_FAILED, // 상품 구매 실패

    PAYMENT_SUCCESS, // 결제 완료
    PAYMENT_FAILED, // 결제 실패

    ORDER_SUCCESS, // 주문 최종 성공
    ORDER_FAILED, // 주문 최종 실패
    ;
}
