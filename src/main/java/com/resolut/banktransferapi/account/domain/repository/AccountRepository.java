package com.resolut.banktransferapi.account.domain.repository;

import com.resolut.banktransferapi.account.domain.model.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findAccountById(Integer id);

}
