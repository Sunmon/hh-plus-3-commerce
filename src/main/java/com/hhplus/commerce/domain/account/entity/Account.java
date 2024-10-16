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

    public void assignId(Long id) {
        this.id = id;
    }

    public Account deposit(Long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("음수 입금 오류");
        }
        this.balance += amount;
        return this;
    }
}
