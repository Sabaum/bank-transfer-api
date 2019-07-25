package com.revolut.banktransferapi.account.domain.repository;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.revolut.banktransferapi.account.domain.model.Account;

import javax.persistence.EntityManager;
import java.util.Optional;

public class JpaAccountRepository implements AccountRepository {

    @Inject
    private Provider<EntityManager> entityManager;

    public Optional<Account> findAccountById(Integer id) {
        return Optional.ofNullable(entityManager.get().find(Account.class, id));
    }

    @Transactional
    @Override
    public void persist(Account account) {
        EntityManager em = this.entityManager.get();
        em.merge(account);
    }
}
