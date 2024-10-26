package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.dto.AccountDepositRequest;
import com.hhplus.commerce.domain.account.dto.AccountResponse;
import com.hhplus.commerce.domain.account.entity.Account;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    final private AccountService accountService;

    @GetMapping(value = "/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long accountId) {


        AccountResponse accountResponse = new AccountResponse(accountService.getAccountInfo(accountId));
        return ResponseEntity.ok(accountResponse);
    }

    @PostMapping(value = "/deposit")
    public ResponseEntity<AccountResponse> deposit(@RequestBody @Valid AccountDepositRequest request) {
        Account account = accountService.deposit(request.accountId(), request.amount());
        AccountResponse accountResponse = new AccountResponse(account);
        return ResponseEntity.ok(accountResponse);
    }
}
