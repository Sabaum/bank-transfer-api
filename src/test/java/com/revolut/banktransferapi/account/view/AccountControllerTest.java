package com.revolut.banktransferapi.account.view;

import com.revolut.banktransferapi.account.service.AccountService;
import com.revolut.banktransferapi.account.view.request.TransferRequest;
import com.revolut.banktransferapi.account.view.response.DataResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    private static final Integer ANY_ID = 1;
    private static final Integer ANY_OTHER_ID = 2;

    @InjectMocks
    private AccountController controller;
    @Mock
    private AccountService service;

    @Test
    public void transfer_withNullRequest_shouldReturnError() {
        Response response = controller.transfer(null);
        Assert.assertEquals("Should fail for null request", "TransferRequest cannot be null", ((DataResponse) response.getEntity()).getError());
    }

    @Test
    public void transfer_withNullValue_shouldReturnError() {
        TransferRequest request = new TransferRequest();

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for null amount", "Amount cannot be null", ((DataResponse) response.getEntity()).getError());
    }

    @Test
    public void transfer_withNullAccountFrom_shouldReturnError() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for null accountFrom", "AccountIdFrom cannot be null", ((DataResponse) response.getEntity()).getError());
   }

    @Test
    public void transfer_withNullAccountTo_shouldReturnError() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(ANY_ID);

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for null accountTo", "AccountIdTo cannot be null", ((DataResponse) response.getEntity()).getError());
    }

    @Test
    public void transfer_withNegativeValue_shouldReturnError() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.valueOf(-1));
        request.setAccountIdFrom(ANY_ID);
        request.setAccountIdTo(ANY_OTHER_ID);

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for negative value", "Amount must be a positive number", ((DataResponse) response.getEntity()).getError());
    }

    @Test
    public void transfer_withZeroValue_shouldReturnError() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.ZERO);
        request.setAccountIdFrom(ANY_ID);
        request.setAccountIdTo(ANY_OTHER_ID);

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for zero value", "Amount must be a positive number", ((DataResponse) response.getEntity()).getError());
    }

    @Test
    public void transfer_withSameAccountFromAndTo_shouldReturnError() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(ANY_ID);
        request.setAccountIdTo(ANY_ID);

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for same account", "Can't transfer to the same account", ((DataResponse) response.getEntity()).getError());
    }

    @Test
    public void transfer_shouldCallServiceTransfer() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(ANY_ID);
        request.setAccountIdTo(ANY_OTHER_ID);

        controller.transfer(request);

        Mockito.verify(service).transfer(request);
    }

    @Test
    public void transfer_shouldReturnSuccess() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(ANY_ID);
        request.setAccountIdTo(ANY_OTHER_ID);

        Response response = controller.transfer(request);
        Assert.assertEquals("Should return success message", "Transfer done", ((DataResponse) response.getEntity()).getMessage());
    }
}