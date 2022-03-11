package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.entity.Account;
import com.acmebank.accountmanager.entity.Transaction;
import com.acmebank.accountmanager.repository.AccountRepository;
import com.acmebank.accountmanager.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccount(long accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public BigDecimal calculateBalance(long accountId) {
        // aggregate all transactions
        Optional<Account> found = getAccount(accountId);
        if (found.isEmpty()) {
            throw new NoSuchElementException("Account of id " + accountId + " not found...");
        }

        List<Transaction> inbound = found.get().getInboundTransactions();
        List<Transaction> outbound = found.get().getOutboundTransactions();

        BigDecimal inboundTotal = inbound.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        BigDecimal outboundTotal = outbound.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        return inboundTotal.subtract(outboundTotal);
    }
}
