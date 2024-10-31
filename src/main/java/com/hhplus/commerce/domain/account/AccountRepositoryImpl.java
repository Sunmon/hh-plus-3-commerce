package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;
import com.hhplus.commerce.domain.account.model.AccountErrorCode;
import com.hhplus.commerce.domain.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    final AccountRepositoryMemoryImpl accountRepositoryMemory;
    final AccountJpaRepository accountJpaRepository;

    @Override
    public Optional<Account> findById(Long accountId) {
        return accountJpaRepository.findById(accountId);
    }

    @Override
    public Account findByIdOrElseThrow(Long accountId) throws IllegalArgumentException {
        return accountJpaRepository.findById(accountId).orElseThrow(() -> new CustomException(AccountErrorCode.ACCOUNT_NOT_EXIST));
    }

    @Override
    public Account insert(Account account) {
        return accountJpaRepository.save(account);
    }

    @Override
    public Account save(Account account) {
        return accountJpaRepository.save(account);
    }

}
