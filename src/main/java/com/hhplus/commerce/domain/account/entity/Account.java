package com.hhplus.commerce.domain.account.entity;

import jakarta.persistence.Entity;
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
}
