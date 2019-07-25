package com.revolut.banktransferapi.account.domain.repository;

import com.revolut.banktransferapi.account.domain.model.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findAccountById(Integer id);

    void persist(Account account);
}
