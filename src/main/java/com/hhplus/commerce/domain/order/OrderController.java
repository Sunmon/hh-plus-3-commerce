package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.common.exception.ErrorResponse;
import com.hhplus.commerce.domain.order.dto.OrderResponse;
import com.hhplus.commerce.domain.order.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/orders")
@Tag(name = "주문/결제 API")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

//    @Operation(summary = "주문 ID를 통한 주문 조회")
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

    @Operation(summary = "주문 ID를 통한 주문 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "잔액 부족", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "상품 재고 부족", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "주문 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
//        OrderResponse orderResponse = new OrderResponse(id, 1L, null, null, null, 0L, null);
        Order order = orderService.getOrder(id);
        return ResponseEntity.ok(new OrderResponse(order));
    }
}