package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.entity.Transaction;

import java.math.BigDecimal;

public interface TransactionService {

    public Transaction transfer(long srcAccountId, long destAccountId, BigDecimal amount);

}
