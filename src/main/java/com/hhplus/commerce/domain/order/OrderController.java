package com.hhplus.commerce.domain.order;

import com.hhplus.commerce.common.exception.ErrorResponse;
import com.hhplus.commerce.domain.order.dto.OrderItems;
import com.hhplus.commerce.domain.order.dto.OrderRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/orders")
@Tag(name = "주문/결제 API")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

//    @Operation(summary = "주문 ID를 통한 주문 조회")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "주문 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
//            @ApiResponse(responseCode = "400", description = "잘못된 파라미터", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = "422", description = "잔액 부족", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = "422", description = "상품 재고 부족", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = "500", description = "주문 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
//    })
//    @PostMapping(value = "/")
//    public ResponseEntity<OrderResponse> order(@PathVariable Long productId, @RequestBody Long userId, @RequestBody int quantity) {
//        OrderResponse orderResponse = new OrderResponse(productId, userId, quantity);
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
    @GetMapping(value = "/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
//        OrderResponse orderResponse = new OrderResponse(productId, 1L, null, null, null, 0L, null);
        Order order = orderService.getOrder(orderId);
        OrderItems orderItems = orderItemService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(new OrderResponse(null, null));
//        return ResponseEntity.ok(null);
    }

    @Operation(summary = "구매 진행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "잔액 부족", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "상품 재고 부족", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "주문 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/")
    public ResponseEntity<OrderResponse> postOrder(@RequestBody OrderRequest orderRequest) {
//        OrderResponse orderResponse = new OrderResponse(productId, 1L, null, null, null, 0L, null);
        // REVIEW - orderService에 orderItemRequest (DTO)를 넘거주는 것이 찝찝합니다.
        // orderItem이라는 Entity를 넣으려고 하니 Product를 매핑해놓아 매번 새 Product 객체를 만들어서 넣는 것도 찝찝합니다.
        // 좋은 방법 없을까요?
//        List<OrderItems> = orderRequest.orderItems().stream().map(orderItem -> new OrderItem(orderItem.productId(), orderItem.quantity())).collect(Collectors.toList());
        Map<Order, OrderItems> order = orderService.order(null, null);
        return ResponseEntity.ok(new OrderResponse(null, null));
    }
}