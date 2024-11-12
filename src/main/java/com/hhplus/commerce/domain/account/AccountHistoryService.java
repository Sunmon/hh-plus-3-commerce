package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.AccountHistory;
import com.hhplus.commerce.domain.account.model.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountHistoryService {

    private final AccountHistoryJpaRepository accountHistoryJpaRepository;

    public List<AccountHistory> getHistories(Long accountId) {
        return accountHistoryJpaRepository.findAllByAccountId(accountId);
    }


    public AccountHistory saveDepositHistory(Long accountId, Long currentBalance, Long transactionAmount, TransactionStatus transactionStatus) {
        Long balance = transactionStatus == TransactionStatus.SUCCESS ? currentBalance + transactionAmount : currentBalance;
        AccountHistory accountHistory = AccountHistory.of(accountId, currentBalance, transactionAmount, balance, TransactionType.DEPOSIT, transactionStatus);
        return accountHistoryJpaRepository.save(accountHistory);
    }


    public void saveWithdrawHistory(Long accountId, Long currentBalance, Long transactionAmount, TransactionStatus transactionStatus) {
        Long balance = transactionStatus == TransactionStatus.SUCCESS ? currentBalance - transactionAmount : currentBalance;
        AccountHistory accountHistory = AccountHistory.of(accountId, currentBalance, transactionAmount, balance, TransactionType.WITHDRAW, transactionStatus);
        accountHistoryJpaRepository.save(accountHistory);
    }
}
