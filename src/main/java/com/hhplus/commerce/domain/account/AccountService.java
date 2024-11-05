package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;
import com.hhplus.commerce.domain.account.entity.AccountHistory;

import java.util.List;

public interface AccountService {

    Account getAccountInfo(Long accountId) throws IllegalArgumentException;

    Account deposit(Long accountId, Long amount) throws IllegalArgumentException;

    Account withdraw(Long accountId, Long amount) throws IllegalArgumentException;

    Account getAccount(Long accountId);

    List<AccountHistory> getHistoriesById(Long accountId);
}
