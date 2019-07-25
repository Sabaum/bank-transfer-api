package com.revolut.banktransferapi.account.service;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.revolut.banktransferapi.account.domain.model.Account;
import com.revolut.banktransferapi.account.domain.repository.AccountRepository;
import com.revolut.banktransferapi.account.view.request.TransferRequest;
import com.revolut.banktransferapi.exception.InvalidOperationException;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountServiceImpl implements AccountService{

    @Inject
    private AccountRepository repository;

    @Transactional
    public final void transfer(TransferRequest transferRequest) {
        if (transferRequest.getAmount() == null) {
            throw new InvalidOperationException("Transfer amount cannot be null");
        }
        if (transferRequest.getAmount().compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidOperationException("Transfer amount must be a positive number");
        }

        Optional<Account> accountFrom = repository.findAccountById(transferRequest.getAccountIdFrom());
        if (!accountFrom.isPresent()) {
            throw new InvalidOperationException("AccountFrom does not exist");
        }

        Optional<Account> accountTo = repository.findAccountById(transferRequest.getAccountIdTo());
        if (!accountTo.isPresent()) {
            throw new InvalidOperationException("AccountTo does not exist");
        }

        executeTransfer(transferRequest.getAmount(), accountFrom.get(), accountTo.get());
    }

    private void executeTransfer(BigDecimal amount, Account accountFrom, Account accountTo) {
        accountFrom.withdrawal(amount);
        accountTo.deposit(amount);

        System.out.println("AccountFrom.balance = " + accountFrom.getBalance());
        System.out.println("AccountTo.balance = " + accountTo.getBalance());

        repository.persist(accountFrom);
        repository.persist(accountTo);

    }

}
