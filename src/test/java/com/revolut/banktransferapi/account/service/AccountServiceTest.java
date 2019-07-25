package com.revolut.banktransferapi.account.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.revolut.banktransferapi.account.domain.model.Account;
import com.revolut.banktransferapi.account.domain.repository.AccountRepository;
import com.revolut.banktransferapi.account.view.request.TransferRequest;
import com.revolut.banktransferapi.exception.InvalidOperationException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.revolut.banktransferapi.account.mock.AccountTemplateLoader.ACCOUNT_FROM_INITIAL_AMOUNT;
import static com.revolut.banktransferapi.account.mock.AccountTemplateLoader.ACCOUNT_TO_INITIAL_AMOUNT;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    private static final Integer ANY_ID = 1;
    private static final Integer ANY_OTHER_ID = 1;

    @Mock
    private AccountRepository repository;
    @InjectMocks
    private AccountServiceImpl service;

    @BeforeClass
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("com.revolut.banktransferapi.account.mock");
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
    public void transferFromInvalidAccount_shouldThrowInvalidOperationException() {
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(ANY_ID);
        Mockito.when(repository.findAccountById(ANY_ID)).thenReturn(Optional.empty());

        service.transfer(request);
    }

    @Test(expected = InvalidOperationException.class)
    public void transferToInvalidAccount_shouldThrowInvalidOperationException() {
        Account validAccountFrom = Fixture.from(Account.class).gimme("validFrom");

        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(validAccountFrom.getId());
        request.setAccountIdTo(ANY_OTHER_ID);

        Mockito.when(repository.findAccountById(Mockito.eq(validAccountFrom.getId()))).thenReturn(Optional.of(new Account()));
        Mockito.when(repository.findAccountById(Mockito.eq(ANY_OTHER_ID))).thenReturn(Optional.empty());

        service.transfer(request);
    }

    @Test
    public void transfer_shouldCallPersistTwice() {
        Account validAccountFrom = Fixture.from(Account.class).gimme("validFrom");
        Account validAccountTo = Fixture.from(Account.class).gimme("validTo");

        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(validAccountFrom.getId());
        request.setAccountIdTo(validAccountTo.getId());

        Mockito.when(repository.findAccountById(validAccountFrom.getId())).thenReturn(Optional.of(validAccountFrom));
        Mockito.when(repository.findAccountById(validAccountTo.getId())).thenReturn(Optional.of(validAccountTo));

        service.transfer(request);

        Mockito.verify(repository, times(2)).persist(Mockito.any(Account.class));

    }

    @Test
    public void transfer_whenCallingPersist_shouldExchange10Dollars() {
        Account validAccountFrom = Fixture.from(Account.class).gimme("validFrom");
        Account validAccountTo = Fixture.from(Account.class).gimme("validTo");

        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setAccountIdFrom(validAccountFrom.getId());
        request.setAccountIdTo(validAccountTo.getId());

        Mockito.when(repository.findAccountById(validAccountFrom.getId())).thenReturn(Optional.of(validAccountFrom));
        Mockito.when(repository.findAccountById(validAccountTo.getId())).thenReturn(Optional.of(validAccountTo));

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        service.transfer(request);

        Mockito.verify(repository, times(2)).persist(accountCaptor.capture());

        List<Account> allCaptors = accountCaptor.getAllValues();
        BigDecimal expectedBalance = ACCOUNT_FROM_INITIAL_AMOUNT.subtract(request.getAmount());
        Assert.assertEquals("Amount should be 10 dollars less than the initial amount",
                expectedBalance, allCaptors.get(0).getBalance());

        expectedBalance = ACCOUNT_TO_INITIAL_AMOUNT.add(request.getAmount());
        Assert.assertEquals("Amount should be 10 dollars more than the initial amount",
                expectedBalance, allCaptors.get(1).getBalance());
    }

}