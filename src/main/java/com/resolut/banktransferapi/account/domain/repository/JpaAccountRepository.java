package com.resolut.banktransferapi.account.domain.repository;

import com.resolut.banktransferapi.account.domain.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class JpaAccountRepository implements AccountRepository {

    private static EntityManagerFactory entityManagerFactory;

    public Optional<Account> findAccountById(Integer id) {
        EntityManager entityManager = openEntityManager();

        return Optional.ofNullable(entityManager.find(Account.class, id));
    }

    @Override
    public void persist(Account account) {
        openEntityManager().persist(account);
    }

    private static EntityManager openEntityManager() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory( "bank-transfer-pu" );
        }
        return entityManagerFactory.createEntityManager();
    }
}
