package com.matt.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.matt.dto.request.AccountCreateRequest;
import com.matt.dto.request.AccountUpdateRequest;
import com.matt.dto.response.AccountResponse;
import com.matt.dto.response.PagingResponse;
import com.matt.entity.Account;

public class AccountConverter {
    public static Account toEntity(AccountCreateRequest request) {
        if (request == null) {
            return null;
        }

        Account account = new Account();
        account.setUserId(request.getUserId());
        account.setCoin(request.getCoin());
        account.setAmount(request.getAmount());
        account.setCreatedAt(System.currentTimeMillis());
        account.setUpdatedAt(System.currentTimeMillis());

        return account;
    }

    public static void toEntity(Account originalAccount, AccountUpdateRequest request) {
        if (originalAccount == null || request == null) {
            return;
        }

        originalAccount.setUserId(request.getUserId());
        originalAccount.setCoin(request.getCoin());
        originalAccount.setAmount(request.getAmount());
        originalAccount.setUpdatedAt(System.currentTimeMillis());
    }

    public static AccountResponse toDTO(Account account) {
        if (account == null) {
            return null;
        }

        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setUserId(account.getUserId());
        response.setCoin(account.getCoin());
        response.setAmount(account.getAmount());
        response.setCreatedBy(account.getCreatedBy());
        response.setUpdatedBy(account.getUpdatedBy());
        response.setCreatedAt(account.getCreatedAt());
        response.setUpdatedAt(account.getUpdatedAt());

        return response;
    }

    public static PagingResponse<AccountResponse> toDTO(Page<Account> accounts) {
        PagingResponse<AccountResponse> response = new PagingResponse<>();

        if (accounts == null || accounts.getRecords() == null || accounts.getRecords().isEmpty()) {
            return response;
        }

        response.setCurrentPage((int) accounts.getCurrent());
        response.setPageSize((int) accounts.getSize());
        response.setTotal(accounts.getTotal());
        response.setPages(accounts.getPages());
        response.setRecords(accounts.getRecords().stream()
                .map(AccountConverter::toDTO)
                .toList());

        return response;
    }
}
