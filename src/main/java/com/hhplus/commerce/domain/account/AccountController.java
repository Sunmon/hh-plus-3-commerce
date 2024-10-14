package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.dto.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
