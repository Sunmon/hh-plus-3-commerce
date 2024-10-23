package com.hhplus.commerce.domain.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private Long id;
    // users 테이블은 본 서비스에서 사실상 사용하지 않기 때문에 빠른 개발을 위해 연관관계를 맺지 않음.
    private Long userId;
    private Long balance;
    
    public static Account of(Long userId, Long balance) {
        return Account.of(null, userId, balance);
    }

    public static Account of(Long id, Long userId, Long balance) {
        return new Account(id, userId, balance);
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public Account deposit(Long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("NOT_VALID_AMOUNT");
        }
        this.balance += amount;
        return this;
    }

    public Account withdraw(Long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("NOT_VALID_AMOUNT");
        }
        if (this.balance - amount < 0) {
            throw new IllegalArgumentException("NOT_ENOUGH_BALANCE");
        }
        this.balance -= amount;
        return this;
    }
}
