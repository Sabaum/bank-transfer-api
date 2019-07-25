package com.revolut.banktransferapi.account.domain.model;

import com.revolut.banktransferapi.exception.InvalidOperationException;
import com.revolut.banktransferapi.exception.OutOfFundsException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Account {

    @Id
    private Integer id;
    @Column(nullable = false, precision = 9, scale = 2)
    private BigDecimal balance;
    @Column(nullable = false)
    private LocalDateTime createDate;

    public Account() {
        this.balance = BigDecimal.ZERO;
        this.createDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
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
        if (balance.compareTo(amount) < 0) {
            throw new OutOfFundsException("Not enough balance available to perform this operation");
        }
        balance = balance.subtract(amount);
    }
}
