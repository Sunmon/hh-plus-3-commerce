package com.hhplus.commerce.domain.user;

import com.hhplus.commerce.common.exception.ErrorResponse;
import com.hhplus.commerce.domain.account.dto.AccountResponse;
import com.hhplus.commerce.domain.user.dto.ChargeRequest;
import com.hhplus.commerce.domain.user.dto.ChargeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
@Tag(name = "유저 잔액 충전/조회 API")
@RequiredArgsConstructor
public class UserController {
//    TODO issue와 동일하게 request, response 맞추기

    //    @PostMapping(value = "/join")
//    @Operation(summary = "상품 정보 조회", response = Product.class)
    @Operation(summary = "유저 잔액 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "잔액 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "404", description = "잘못된 파라미터", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "잔액 조회 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{productId}/balance")
    public ResponseEntity<AccountResponse> getBalance(@PathVariable Long id) {
        AccountResponse accountResponse = new AccountResponse(id, id, 10000L);
        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "유저 잔액 충전")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "잔액 충전 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "잔액 충전 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping(value = "/{productId}/balance")
    public ResponseEntity<ChargeResponse> charge(@PathVariable Long id, @RequestBody ChargeRequest chargeRequest) {
        ChargeResponse chargeResponse = new ChargeResponse(id, chargeRequest.amount(), 10000L);
        return ResponseEntity.ok(chargeResponse);
    }
}