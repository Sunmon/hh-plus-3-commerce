package com.hhplus.commerce.domain;

import com.hhplus.commerce.domain.account.dto.AccountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/accounts")
public class AccountController {

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {
        AccountResponse accountResponse = new AccountResponse(id, 10000L);
        return ResponseEntity.ok(accountResponse);
    }

}
