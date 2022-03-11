package com.acmebank.accountmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "SRC_ACCOUNT_ID")
    private Account source;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "DEST_ACCOUNT_ID")
    private Account destination;

    private BigDecimal amount;

    @Column(name = "TS", insertable = false, updatable = false)
    private Instant timestamp;

    public Long getId() {
        return id;
    }

    public Transaction setId(Long id) {
        this.id = id;
        return this;
    }

    public Account getSource() {
        return source;
    }

    public Transaction setSource(Account source) {
        this.source = source;
        return this;
    }

    public Account getDestination() {
        return destination;
    }

    public Transaction setDestination(Account destination) {
        this.destination = destination;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Transaction setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
