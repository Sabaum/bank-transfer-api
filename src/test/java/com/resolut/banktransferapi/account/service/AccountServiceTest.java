package com.resolut.banktransferapi.account.service;

import com.resolut.banktransferapi.account.domain.model.Account;
import com.resolut.banktransferapi.account.domain.repository.AccountRepository;
import com.resolut.banktransferapi.exception.InvalidOperationException;
import com.resolut.banktransferapi.transfer.view.request.TransferRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountServiceTest {

    private static final Integer ANY_ID = 1;

    private AccountRepository repository;
    private AccountService service;

    @Before
    public void setUp() {
        repository = Mockito.mock(AccountRepository.class);
        service = new AccountServiceImpl(repository);
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

    private Account account() {
        return new Account();
    }

}