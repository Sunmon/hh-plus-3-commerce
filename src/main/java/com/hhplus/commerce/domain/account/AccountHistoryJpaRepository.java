package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountHistoryJpaRepository extends JpaRepository<AccountHistory, Long> {
    List<AccountHistory> findAllByAccountId(Long accountId);
    
}
