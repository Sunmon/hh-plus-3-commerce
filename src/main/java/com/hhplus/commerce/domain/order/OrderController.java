package com.hhplus.commerce.domain.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/orders")
@Tag(name = "주문/결제 API - 사용하지 않음 (상품API에서 주문/결제 처리)")
@RequiredArgsConstructor
public class OrderController {
//    @Operation(summary = "주문/결제")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "주문 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
//            @ApiResponse(responseCode = "400", description = "잘못된 파라미터", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = "422", description = "잔액 부족", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = "422", description = "상품 재고 부족", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = "500", description = "주문 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
//    })
//    @PostMapping(value = "/")
//    public ResponseEntity<OrderResponse> order(@PathVariable Long id, @RequestBody Long userId, @RequestBody int quantity) {
//        OrderResponse orderResponse = new OrderResponse(id, userId, quantity);
//        return ResponseEntity.ok(orderResponse);
//    }
}