package com.acmebank.accountmanager.dto;

import java.math.BigDecimal;

public class BalanceDto {

    public Long accountId;
    public BigDecimal value;

    public BalanceDto() {
    }

    public BalanceDto(Long accountId, BigDecimal value) {
        this.accountId = accountId;
        this.value = value;
    }

    public Long getAccountId() {
        return accountId;
    }

    public BalanceDto setAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BalanceDto setValue(BigDecimal value) {
        this.value = value;
        return this;
    }
}
