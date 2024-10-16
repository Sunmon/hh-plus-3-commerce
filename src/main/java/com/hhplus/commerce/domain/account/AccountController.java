package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.dto.AccountDepositRequest;
import com.hhplus.commerce.domain.account.dto.AccountResponse;
import com.hhplus.commerce.domain.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    final private AccountService accountService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {

//        AccountResponse accountResponse = new AccountResponse(id, 10000L);
        AccountResponse accountResponse = new AccountResponse(accountService.getAccountInfo(id));
        return ResponseEntity.ok(accountResponse);
    }

    @PostMapping(value = "/deposit")
    public ResponseEntity<AccountResponse> deposit(@RequestBody AccountDepositRequest request) {
        Account account = accountService.deposit(request.accountId(), request.amount());
        AccountResponse accountResponse = new AccountResponse(account);
        return ResponseEntity.ok(accountResponse);
    }

}
