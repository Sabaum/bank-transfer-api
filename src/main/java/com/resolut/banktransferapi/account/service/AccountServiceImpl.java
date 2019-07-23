package com.resolut.banktransferapi.account.service;

import com.resolut.banktransferapi.account.domain.model.Account;
import com.resolut.banktransferapi.account.domain.repository.AccountRepository;
import com.resolut.banktransferapi.exception.InvalidOperationException;
import com.resolut.banktransferapi.account.view.request.TransferRequest;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;

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

        Optional<Account> accountById = repository.findAccountById(transferRequest.getAccountIdFrom());
        if (accountById.isPresent()) {
            //TODO continue with transfer operation

        } else {
            throw new InvalidOperationException("Account does not exist");
        }

    }

}
