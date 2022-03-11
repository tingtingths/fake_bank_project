package com.acmebank.accountmanager.dto;

import com.acmebank.accountmanager.entity.Transaction;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.Instant;

public class TransactionDto {

    private Long source;
    private Long destination;
    private BigDecimal amount;
    private Instant timestamp;

    public TransactionDto() {
    }

    public TransactionDto(Transaction transaction) {
        this.source = transaction.getSource() == null ? null : transaction.getSource().getId();
        this.destination = transaction.getDestination().getId();
        this.amount = transaction.getAmount();
        this.timestamp = transaction.getTimestamp();
    }

    public Long getSource() {
        return source;
    }

    public TransactionDto setSource(Long source) {
        this.source = source;
        return this;
    }

    public Long getDestination() {
        return destination;
    }

    public TransactionDto setDestination(Long destination) {
        this.destination = destination;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionDto setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public TransactionDto setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
