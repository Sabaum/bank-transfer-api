package com.resolut.banktransferapi.account.domain.model;

import com.resolut.banktransferapi.exception.InvalidOperationException;
import com.resolut.banktransferapi.exception.OutOfFundsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountTest {

    @Before
    public void setUp() {
    }

    @Test
    public void createAccount_balanceShouldBeZero() {
        Account account = Account.create();

        Assert.assertEquals("Balance should be 0", BigDecimal.ZERO, account.getBalance());
    }

    @Test
    public void createAccount_createDateShouldBeNotNull() {
        Account account = Account.create();

        Assert.assertNotNull("CreateDate should not be null", account.getCreateDate());
    }

    @Test
    public void depositTenDollars_balanceShouldIncrementBy10() {
        Account account = Account.create();
        account.deposit(BigDecimal.TEN);

        Assert.assertEquals("Balance should be 10", BigDecimal.TEN, account.getBalance());
    }

    @Test(expected = InvalidOperationException.class)
    public void depositNegativeValue_shouldThrowInvalidOperationException() {
        Account account = Account.create();
        account.deposit(BigDecimal.valueOf(-1));
    }

    @Test(expected = InvalidOperationException.class)
    public void depositNullValue_shouldThrowInvalidOperationException() {
        Account account = Account.create();
        account.deposit(null);
    }

    @Test
    public void withdrawalTenDollars_balanceShouldDecrementBy10() {
        Account account = positiveAccount();
        account.withdrawal(BigDecimal.TEN);

        Assert.assertEquals("Balance should be 90.", BigDecimal.valueOf(90), account.getBalance());
    }

    @Test(expected = InvalidOperationException.class)
    public void withdrawalPositiveValue_shouldThrowInvalidOperationException() {
        Account account = positiveAccount();
        account.withdrawal(BigDecimal.valueOf(-1));
    }

    @Test(expected = InvalidOperationException.class)
    public void withdrawalNullValue_shouldThrowInvalidOperationException() {
        Account account = positiveAccount();
        account.withdrawal(null);
    }

    @Test(expected = OutOfFundsException.class)
    public void withdrawalTenDollarsWithNoFunds_shouldThrowOutOfFundsException() {
        Account account = Account.create();
        account.withdrawal(BigDecimal.TEN);
    }

    private Account positiveAccount() {
        Account account = Account.create();
        account.deposit(BigDecimal.valueOf(100));

        return account;
    }
}