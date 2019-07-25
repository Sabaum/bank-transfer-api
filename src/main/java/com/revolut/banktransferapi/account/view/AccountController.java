package com.revolut.banktransferapi.account.view;

import com.google.inject.Inject;
import com.revolut.banktransferapi.account.service.AccountService;
import com.revolut.banktransferapi.account.view.request.TransferRequest;
import com.revolut.banktransferapi.account.view.response.DataResponse;
import com.revolut.banktransferapi.exception.InvalidOperationException;
import com.revolut.banktransferapi.exception.ValidationException;
import com.revolut.banktransferapi.util.Constants;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/api/account")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    @Inject
    private AccountService service;

    @POST
    @Path("/transfer")
    public Response transfer(TransferRequest request) {
        try {
            validateTransferRequest(request);
            service.transfer(request);

            return Response.ok(DataResponse.success("Transfer done")).build();

        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(DataResponse.error(e.getMessage())).build();
        }
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
