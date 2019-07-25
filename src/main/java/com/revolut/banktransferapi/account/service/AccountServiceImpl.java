package com.revolut.banktransferapi.account.service;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.revolut.banktransferapi.account.domain.model.Account;
import com.revolut.banktransferapi.account.domain.repository.AccountRepository;
import com.revolut.banktransferapi.account.view.request.TransferRequest;
import com.revolut.banktransferapi.exception.InvalidOperationException;
import com.revolut.banktransferapi.util.Validator;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountServiceImpl implements AccountService{

    @Inject
    private AccountRepository repository;

    @Transactional
    public final void transfer(TransferRequest transferRequest) {

        Optional<Account> accountFrom = repository.findAccountById(transferRequest.getAccountIdFrom());
        Optional<Account> accountTo = repository.findAccountById(transferRequest.getAccountIdTo());

        validateBeforeTransfer(transferRequest, accountFrom, accountTo);

        executeTransfer(transferRequest.getAmount(), accountFrom.get(), accountTo.get());
    }

    private void validateBeforeTransfer(TransferRequest transferRequest, Optional<Account> accountFrom, Optional<Account> accountTo) {
        Validator.NOT_NULL.validate(transferRequest.getAmount(), "Transfer amount");
        Validator.POSITIVE_NUMBER.validate(transferRequest.getAmount(), "Transfer amount");
        Validator.NOT_EMPTY_OPTIONAL.validate(accountFrom, "AccountFrom");
        Validator.NOT_EMPTY_OPTIONAL.validate(accountTo, "AccountTo");
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
