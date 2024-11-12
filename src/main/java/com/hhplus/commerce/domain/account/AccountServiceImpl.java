package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;
import com.hhplus.commerce.domain.account.entity.AccountHistory;
import com.hhplus.commerce.domain.account.model.TransactionStatus;
import com.hhplus.commerce.domain.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public
class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountHistoryService accountHistoryService;

    @Override
    public Account getAccountInfo(Long accountId) throws IllegalArgumentException {
        return accountRepository.findByIdOrElseThrow(accountId);
    }

    @Override
    public Account deposit(Long accountId, Long amount) throws RuntimeException {
        Account account = getAccountInfo(accountId);
        Long currentBalance = account.getBalance();

        try {
            account.deposit(amount);
            accountRepository.save(account);
            accountHistoryService.saveDepositHistory(account.getId(), currentBalance, amount, TransactionStatus.SUCCESS);
        } catch (CustomException e) {
            accountHistoryService.saveDepositHistory(account.getId(), currentBalance, amount, TransactionStatus.FAIL);
            throw e;
        }

        return account;
    }

    @Override
    public Account withdraw(Long accountId, Long amount) throws IllegalArgumentException {
        Account account = getAccountInfo(accountId);
        Long currentBalance = account.getBalance();
        try {
            account.withdraw(amount);
            accountRepository.save(account);
            accountHistoryService.saveWithdrawHistory(account.getId(), currentBalance, amount, TransactionStatus.SUCCESS);
        } catch (CustomException e) {
            accountHistoryService.saveWithdrawHistory(account.getId(), currentBalance, amount, TransactionStatus.FAIL);
            throw e;
        }

        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(Long accountId) {
        return null;
    }

    @Override
    public List<AccountHistory> getHistoriesById(Long accountId) {
        return null;
    }
}
