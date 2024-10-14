package com.hhplus.commerce.domain.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;
    private Long balance;

    public Account(Long id) {
        this.id = id;
        this.balance = 0L;
    }

    public void assignId(Long id) {
        this.id = id;
    }
}
