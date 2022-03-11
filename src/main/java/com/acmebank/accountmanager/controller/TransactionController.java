package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.dto.TransactionDto;
import com.acmebank.accountmanager.entity.Transaction;
import com.acmebank.accountmanager.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public TransactionDto createTransaction(@RequestBody TransactionDto newTrx) {
        try {
            Transaction completedTrx = transactionService.transfer(newTrx.getSource(), newTrx.getDestination(), newTrx.getAmount());
            return new TransactionDto(completedTrx);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }
}
