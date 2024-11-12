package com.hhplus.commerce.domain.account.entity;

import com.hhplus.commerce.domain.account.TransactionType;
import com.hhplus.commerce.domain.account.model.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 연관관계 매핑하지 않음
    Long accountId;

    Long currentBalance;

    Long transactionAmount;

    Long newBalance;

    TransactionType transactionType;

    TransactionStatus transactionStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static AccountHistory of(Long accountId, Long currentBalance, Long transactionAmount, Long newBalance, TransactionType transactionType, TransactionStatus transactionStatus) {
        return new AccountHistory(null, accountId, currentBalance, transactionAmount, newBalance, transactionType, transactionStatus, LocalDateTime.now(), LocalDateTime.now());
    }

    public void updateTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

}
