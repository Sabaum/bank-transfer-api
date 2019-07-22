package com.resolut.banktransferapi.view;

import com.resolut.banktransferapi.account.service.AccountService;
import com.resolut.banktransferapi.exception.InvalidOperationException;
import com.resolut.banktransferapi.view.request.TransferRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class AccountControllerTest {

    private static final Integer ANY_ID = 1;
    private static final Integer ANY_OTHER_ID = 2;

    private AccountController controller;
    private AccountService service;

    @Before
    public void setUp() {
        service = Mockito.mock(AccountService.class);
        controller = new AccountController(service);
    }

    @Test(expected = InvalidOperationException.class)
    public void transfer_withNullRequest_shouldThrowInvalidOperationException() {
        controller.transfer(null);
    }

    @Test(expected = InvalidOperationException.class)
    public void transfer_withNullValue_shouldThrowInvalidOperationException() {
        TransferRequest request = new TransferRequest();

        controller.transfer(request);
    }

    @Test(expected = InvalidOperationException.class)
    public void transfer_withNullAccountFrom_shouldThrowInvalidOperationException() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);

        controller.transfer(request);
    }

    @Test(expected = InvalidOperationException.class)
    public void transfer_withNullAccountTo_shouldThrowInvalidOperationException() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(ANY_ID);

        controller.transfer(request);
    }

    @Test(expected = InvalidOperationException.class)
    public void transfer_withNegativeValue_shouldThrowInvalidOperationException() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.valueOf(-1));
        request.setAccountIdFrom(ANY_ID);
        request.setAccountIdTo(ANY_OTHER_ID);

        controller.transfer(request);
    }

    @Test(expected = InvalidOperationException.class)
    public void transfer_withZeroValue_shouldThrowInvalidOperationException() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.ZERO);
        request.setAccountIdFrom(ANY_ID);
        request.setAccountIdTo(ANY_OTHER_ID);

        controller.transfer(request);
    }

    @Test(expected = InvalidOperationException.class)
    public void transfer_withSameAccountFromAndTo_shouldThrowInvalidOperationException() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(ANY_ID);
        request.setAccountIdTo(ANY_ID);

        controller.transfer(request);
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