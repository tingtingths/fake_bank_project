package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> getAll();

    Optional<Account> getAccount(long accountId);

    BigDecimal calculateBalance(long accountId);

}
