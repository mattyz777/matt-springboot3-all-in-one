package com.matt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.matt.constant.Constant;
import com.matt.converter.AccountConverter;
import com.matt.dto.request.AccountCreateRequest;
import com.matt.dto.request.AccountQueryRequest;
import com.matt.dto.request.AccountUpdateRequest;
import com.matt.dto.request.PagingRequest;
import com.matt.dto.response.AccountResponse;
import com.matt.dto.response.PagingResponse;
import com.matt.entity.Account;
import com.matt.repository.AccountRepository;
import com.matt.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl extends ServiceImpl<AccountRepository, Account> implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Long createAccount(AccountCreateRequest accountRequest) {
        Account account = AccountConverter.toEntity(accountRequest);
        account.setCreatedBy(Constant.USER_ADMIN);
        super.save(account);
        return account.getId();
    }

    @Override
    public AccountResponse getAccountById(Long id) {
        Account account = super.getById(id);
        if(account == null) {
            return null;
        }
        return AccountConverter.toDTO(account);
    }

    @Override
    public PagingResponse<AccountResponse> getAccountPage(PagingRequest<AccountQueryRequest> request) {
        AccountQueryRequest requestBody = request.getRequestBody();
        if(requestBody == null) {
            throw new RuntimeException("query request body AccountQueryRequest is null");
        }

        Page<Account> pagingAccount = new Page<>(request.getCurrentPage(), request.getPageSize());
        this.accountRepository.listAccounts(pagingAccount, requestBody);

        return AccountConverter.toDTO(pagingAccount);
    }

    @Override
    public Boolean updateAccount(Long accountId, AccountUpdateRequest accountRequest) {
        Account account = getById(accountId);
        if(account == null) {
            throw new IllegalArgumentException("Account doesn't exist ID:" + accountId);
        }

        AccountConverter.toEntity(account, accountRequest);
        account.setUpdatedBy(Constant.USER_ADMIN);
        return super.updateById(account);
    }

    @Override
    public Long deleteAccount(Long id) {
        Account account = getById(id);
        if(account == null) {
            throw new IllegalArgumentException("账户不存在，ID: " + id);
        }

        account.setUpdatedBy(Constant.USER_ADMIN);
        account.setUpdatedAt(System.currentTimeMillis());
        // login delete handled by  @TableLogic
        boolean success = super.removeById(id);
        if (!success) {
            throw new RuntimeException("账户删除失败，ID: " + id);
        }

        return id;
    }
}
