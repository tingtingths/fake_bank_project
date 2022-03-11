package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.entity.Account;
import com.acmebank.accountmanager.entity.Transaction;
import com.acmebank.accountmanager.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private AccountService accountService;
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountService accountService, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    // naive synchronized modifier
    @Override
    public synchronized Transaction transfer(long srcAccountId, long destAccountId, BigDecimal amount) {
        Optional<Account> sourceFound = accountService.getAccount(srcAccountId);
        Optional<Account> destFound = accountService.getAccount(destAccountId);

        // fail fast
        if (sourceFound.isEmpty()) {
            throw new NoSuchElementException("Account of id " + srcAccountId + " not found...");
        }
        if (destFound.isEmpty()) {
            throw new NoSuchElementException("Account of id " + destAccountId + " not found...");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be > 0...");
        }

        Account source = sourceFound.get();
        Account destination = destFound.get();

        // check balance
        if (accountService.calculateBalance(source.getId()).compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance...");
        }

        Transaction transaction = new Transaction()
                .setSource(source)
                .setDestination(destination)
                .setAmount(amount)
                .setTimestamp(Instant.now());
        return transactionRepository.save(transaction);
    }
}
