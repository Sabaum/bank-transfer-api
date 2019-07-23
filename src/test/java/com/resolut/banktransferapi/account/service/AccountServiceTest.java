package com.resolut.banktransferapi.account.service;

import com.resolut.banktransferapi.account.domain.repository.AccountRepository;
import com.resolut.banktransferapi.account.view.request.TransferRequest;
import com.resolut.banktransferapi.exception.InvalidOperationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    private static final Integer ANY_ID = 1;

    @Mock
    private AccountRepository repository;
    @InjectMocks
    private AccountServiceImpl service;

    @Before
    public void setUp() {
    }

    @Test(expected = InvalidOperationException.class)
    public void transferNullValue_shouldThrowInvalidOperationException() {
        TransferRequest request = new TransferRequest();

        service.transfer(request);
    }

    @Test(expected = InvalidOperationException.class)
    public void transferNegativeValue_shouldThrowInvalidOperationException() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.valueOf(-1));

        service.transfer(request);
    }

    @Test(expected = InvalidOperationException.class)
    public void transferZeroValue_shouldThrowInvalidOperationException() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.ZERO);

        service.transfer(request);
    }

    @Test(expected = InvalidOperationException.class)
    public void transferForInvalidAccount_shouldThrowInvalidOperationException() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(ANY_ID);
        Mockito.when(repository.findAccountById(ANY_ID)).thenReturn(Optional.empty());

        service.transfer(request);
    }

}