package com.revolut.banktransferapi.account.domain.repository;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.google.inject.Provider;
import com.revolut.banktransferapi.account.domain.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;

@RunWith(MockitoJUnitRunner.class)
public class JpaAccountRepositoryTest {

    private static final Integer ANY_ID = 1;

    @InjectMocks
    private JpaAccountRepository repository;
    @Mock
    private Provider<EntityManager> entityManagerProvider;

    private EntityManager entityManager = Mockito.mock(EntityManager.class);

    @Before
    public void setUp() {
        FixtureFactoryLoader.loadTemplates("com.revolut.banktransferapi.account.mock");
        Mockito.when(entityManagerProvider.get()).thenReturn(entityManager);
    }

    @Test
    public void findAccountById_shouldCallEntityManagerFind() {
        repository.findAccountById(ANY_ID);

        Mockito.verify(entityManager).find(Account.class, ANY_ID);
    }

    @Test
    public void persist_shouldCallEntityManagerPersist() {
        Account validAccountFrom = Fixture.from(Account.class).gimme("validFrom");
        repository.persist(validAccountFrom);

        Mockito.verify(entityManager).merge(validAccountFrom);
    }
}