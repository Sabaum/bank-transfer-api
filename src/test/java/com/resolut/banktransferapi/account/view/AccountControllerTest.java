package com.resolut.banktransferapi.account.view;

import com.resolut.banktransferapi.account.service.AccountService;
import com.resolut.banktransferapi.account.view.request.TransferRequest;
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
        Assert.assertEquals("Should fail for null request", "TransferRequest must be valid", response.getEntity());
    }

    @Test
    public void transfer_withNullValue_shouldReturnError() {
        TransferRequest request = new TransferRequest();

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for null amount", "Amount must be valid", response.getEntity());
    }

    @Test
    public void transfer_withNullAccountFrom_shouldReturnError() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for null accountFrom", "AccountIdFrom must be valid", response.getEntity());
   }

    @Test
    public void transfer_withNullAccountTo_shouldReturnError() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(ANY_ID);

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for null accountTo", "AccountIdTo must be valid", response.getEntity());
    }

    @Test
    public void transfer_withNegativeValue_shouldReturnError() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.valueOf(-1));
        request.setAccountIdFrom(ANY_ID);
        request.setAccountIdTo(ANY_OTHER_ID);

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for negative value", "Amount must be a positive number", response.getEntity());
    }

    @Test
    public void transfer_withZeroValue_shouldReturnError() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.ZERO);
        request.setAccountIdFrom(ANY_ID);
        request.setAccountIdTo(ANY_OTHER_ID);

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for zero value", "Amount must be a positive number", response.getEntity());
    }

    @Test
    public void transfer_withSameAccountFromAndTo_shouldReturnError() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(ANY_ID);
        request.setAccountIdTo(ANY_ID);

        Response response = controller.transfer(request);
        Assert.assertEquals("Should fail for same account", "Can't transfer to the same account", response.getEntity());
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
}