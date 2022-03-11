package com.acmebank.accountmanager.dto;

import com.acmebank.accountmanager.entity.Account;

public class AccountDto {
    private Long id;
    private String name;

    public AccountDto() {
    }

    public AccountDto(Account account) {
        this.id = account.getId();
        this.name = account.getName();
    }

    public Long getId() {
        return id;
    }

    public AccountDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AccountDto setName(String name) {
        this.name = name;
        return this;
    }
}
