package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.dto.AccountDto;
import com.acmebank.accountmanager.dto.BalanceDto;
import com.acmebank.accountmanager.dto.TransactionDto;
import com.acmebank.accountmanager.entity.Account;
import com.acmebank.accountmanager.entity.Transaction;
import com.acmebank.accountmanager.repository.AccountRepository;
import com.acmebank.accountmanager.repository.TransactionRepository;
import com.acmebank.accountmanager.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /*
        Story 1) As a customer I would like to get the balance of my account
        Story 2) As a customer I would like to be able to transfer an amount in HKD,
                from one account to another account
     */

    @GetMapping
    public List<AccountDto> getAllAccounts() {
        return accountService.getAll().parallelStream()
                .map(AccountDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable long id) {
        Optional<Account> found = accountService.getAccount(id);
        if (found.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Account of ID " + id + " not found..."
            );
        }
        return new AccountDto(found.get());
    }

    @GetMapping("/{id}/balance")
    public BalanceDto getBalance(@PathVariable long id) {
        try {
            BigDecimal balance = accountService.calculateBalance(id);
            return new BalanceDto(id, balance);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Account of ID " + id + " not found..."
            );
        }
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionDto> getTransactions(@PathVariable long id) {
        Optional<Account> found = accountService.getAccount(id);
        if (found.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Account of ID " + id + " not found..."
            );
        }
        Account account = found.get();

        // sort by time desc
        return Stream.concat(account.getInboundTransactions().stream(), account.getOutboundTransactions().stream())
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .map(TransactionDto::new)
                .collect(Collectors.toList());
    }
}
