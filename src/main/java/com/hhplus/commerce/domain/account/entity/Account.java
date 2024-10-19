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
    private Long balance;
    private Long userId;

    public Account(Long id) {
        this.id = id;
        this.balance = 0L;
        this.userId = null;
    }

    public static Account of(Long id, long balance, Long userId) {
        return new Account(id, balance, userId);
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
