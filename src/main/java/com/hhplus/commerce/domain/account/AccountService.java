package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;

public interface AccountService {

    Account getAccountInfo(Long accountId) throws IllegalArgumentException;

    Account deposit(Long accountId, Long amount) throws IllegalArgumentException;
}
