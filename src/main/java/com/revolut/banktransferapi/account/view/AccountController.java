package com.revolut.banktransferapi.account.view;

import com.google.inject.Inject;
import com.revolut.banktransferapi.account.service.AccountService;
import com.revolut.banktransferapi.account.view.request.TransferRequest;
import com.revolut.banktransferapi.account.view.response.DataResponse;
import com.revolut.banktransferapi.exception.InvalidOperationException;
import com.revolut.banktransferapi.exception.ValidationException;
import com.revolut.banktransferapi.util.Constants;
import com.revolut.banktransferapi.util.Validator;

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
        Validator.NOT_NULL.validate(request, "TransferRequest");
        Validator.NOT_NULL.validate(request.getAmount(), "Amount");
        Validator.NOT_NULL.validate(request.getAccountIdFrom(), "AccountIdFrom");
        Validator.NOT_NULL.validate(request.getAccountIdTo(), "AccountIdTo");
        Validator.POSITIVE_NUMBER.validate(request.getAmount(), "Amount");
        Validator.validateNotEquals(request.getAccountIdFrom(), request.getAccountIdTo());
    }

}
