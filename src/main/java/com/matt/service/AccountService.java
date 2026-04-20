package com.matt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.matt.dto.request.AccountCreateRequest;
import com.matt.dto.request.AccountQueryRequest;
import com.matt.dto.request.AccountUpdateRequest;
import com.matt.dto.request.PagingRequest;
import com.matt.dto.response.AccountResponse;
import com.matt.dto.response.PagingResponse;
import com.matt.entity.Account;

import java.util.List;

public interface AccountService extends IService<Account> {
    Long createAccount(AccountCreateRequest account);
    Boolean updateAccount(Long id, AccountUpdateRequest account);
    Long deleteAccount(Long id);
    AccountResponse getAccountById(Long id);
    PagingResponse<AccountResponse> getAccountPage(PagingRequest<AccountQueryRequest> request);
}
