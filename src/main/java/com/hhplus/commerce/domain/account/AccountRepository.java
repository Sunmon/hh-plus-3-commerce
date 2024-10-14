package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;

public interface AccountRepository {
    Account findById(Long accountId);
}
