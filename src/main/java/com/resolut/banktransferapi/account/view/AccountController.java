package com.resolut.banktransferapi.account.view;

import com.resolut.banktransferapi.account.service.AccountService;
import com.resolut.banktransferapi.exception.InvalidOperationException;
import com.resolut.banktransferapi.account.view.request.TransferRequest;
import com.resolut.banktransferapi.util.Constants;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("/account")
public class AccountController {

    private final AccountService service;

    @Inject
    public AccountController(AccountService service) {
        this.service = service;
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    public void transfer(TransferRequest request) {
        validateTransferRequest(request);

        service.transfer(request);
    }

    private void validateTransferRequest(TransferRequest request) {
        validateNullObject(request, "TransferRequest");
        validateNullObject(request.getAmount(), "Amount");
        validateNullObject(request.getAccountIdFrom(), "AccountIdFrom");
        validateNullObject(request.getAccountIdTo(), "AccountIdTo");
        if (request.getAmount().compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidOperationException(Constants.AMOUNT_MUST_BE_POSITIVE);
        }
        if (request.getAccountIdFrom().equals(request.getAccountIdTo())) {
            throw new InvalidOperationException(Constants.SAME_ACCOUNT_TRANSFER);
        }
    }

    private void validateNullObject(Object obj, String fieldName) {
        if (obj == null) {
            throw new InvalidOperationException(String.format(Constants.INVALID_OBJECT_MESSAGE, fieldName));
        }
    }
}
