package com.resolut.banktransferapi.view;

import com.resolut.banktransferapi.account.service.AccountService;
import com.resolut.banktransferapi.exception.InvalidOperationException;
import com.resolut.banktransferapi.view.request.TransferRequest;

import java.math.BigDecimal;

public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    public void transfer(TransferRequest request) {
        if (request == null) {
            throw new InvalidOperationException("TransferRequest must be valid");
        }
        if (request.getAmount() == null) {
            throw new InvalidOperationException("Amount must be valid");
        }
        if (request.getAccountIdFrom() == null) {
            throw new InvalidOperationException("AccountIdFrom must be valid");
        }
        if (request.getAccountIdTo() == null) {
            throw new InvalidOperationException("AccountIdTo must be valid");
        }
        if (request.getAmount().compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidOperationException("Amount must be a positive number");
        }
        if (request.getAccountIdFrom().equals(request.getAccountIdTo())) {
            throw new InvalidOperationException("Can't transfer to the same account");
        }

        service.transfer(request);
    }
}
