package com.resolut.banktransferapi.account.service;

import com.resolut.banktransferapi.account.domain.model.Account;
import com.resolut.banktransferapi.account.domain.repository.AccountRepository;
import com.resolut.banktransferapi.exception.InvalidOperationException;
import com.resolut.banktransferapi.account.view.request.TransferRequest;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Optional;

@Named
@Default
public class AccountServiceImpl implements AccountService{

    @Inject
    private AccountRepository repository;

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

        repository.persist(accountFrom);
        repository.persist(accountTo);
    }

}
