package com.resolut.banktransferapi.account.domain.model;

import com.resolut.banktransferapi.exception.InvalidOperationException;
import com.resolut.banktransferapi.exception.OutOfFundsException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Account {

    @Id
    private Integer id;
    @Column(nullable = false, precision = 2)
    private BigDecimal balance;
    @Column(nullable = false)
    private LocalDateTime createDate;

    public Account() {
        this.balance = BigDecimal.ZERO;
        this.createDate = LocalDateTime.now();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public static Account create() {
        return new Account();
    }

    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidOperationException("Amount should be greater than Zero");
        }
        balance = balance.add(amount);
    }

    public void withdrawal(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidOperationException("Amount should be greater than Zero");
        }
        if (balance.compareTo(amount) < 1) {
            throw new OutOfFundsException("Not enough balance available to perform this operation");
        }
        balance = balance.subtract(amount);
    }
}
