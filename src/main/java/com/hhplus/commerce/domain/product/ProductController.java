package com.hhplus.commerce.domain.product;

import com.hhplus.commerce.common.exception.ErrorResponse;
import com.hhplus.commerce.domain.product.dto.ProductOrderRequest;
import com.hhplus.commerce.domain.product.dto.ProductOrderResponse;
import com.hhplus.commerce.domain.product.dto.ProductResponse;
import com.hhplus.commerce.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/products")
@Tag(name = "상품 API")
@RequiredArgsConstructor
public class ProductController {
    final private ProductService productService;

    @Operation(summary = "상품 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 정보 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "상품 정보 조회 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> productInfo(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(new ProductResponse(product));
    }

    @Operation(summary = "상품 주문/결제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "잔액 부족", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "상품 재고 부족", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "주문 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/{id}/orders")
    public ResponseEntity<ProductOrderResponse> order(@PathVariable Long id, @RequestBody ProductOrderRequest productOrderRequest) {
        ProductOrderResponse productOrderResponse = new ProductOrderResponse(id, productOrderRequest.userId(), productOrderRequest.quantity());
        return ResponseEntity.ok(productOrderResponse);
    }

    @Operation(summary = "상위 상품 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인기상품 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductOrderResponse.class))),
            @ApiResponse(responseCode = "500", description = "인기상품 조회 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/top")
    public ResponseEntity<List<ProductResponse>> productsTop() {
        List<ProductResponse> productResponse = List.of(
//                new ProductResponse(1L, "상품명1", 10000, 123),
//                new ProductResponse(2L, "상품명2", 10000, 123),
//                new ProductResponse(3L, "상품명3", 10000, 123),
//                new ProductResponse(4L, "상품명4", 10000, 123),
//                new ProductResponse(5L, "상품명5", 10000, 123)
        );

        return ResponseEntity.ok(productResponse);
    }
}