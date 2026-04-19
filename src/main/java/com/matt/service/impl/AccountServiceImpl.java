package com.matt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.matt.entity.Account;
import com.matt.repository.AccountRepository;
import com.matt.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountRepository, Account> implements AccountService {

    @Override
    public Account getAccountByUserIdAndCoin(Long userId, String coin) {
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getUserId, userId)
                .eq(Account::getCoin, coin);
        return this.getOne(wrapper);
    }

    @Override
    public List<Account> getAccountsByUserId(Long userId) {
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getUserId, userId);
        return this.list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBalance(Long accountId, BigDecimal amount) {
        Account account = this.getById(accountId);
        if (account == null) {
            return false;
        }

        account.setAmount(account.getAmount().add(amount));
        account.setUpdatedAt(System.currentTimeMillis());

        return this.updateById(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(Account account) {
        account.setCreatedAt(System.currentTimeMillis());
        account.setUpdatedAt(System.currentTimeMillis());
        return this.save(account);
    }

    @Override
    public Page<Account> getAccountPage(Integer currentPage, Integer pageSize) {
        Page<Account> page = new Page<>(currentPage, pageSize);
        return this.page(page);
    }

    @Override
    public Page<Account> getAccountPageByUserId(Long userId, Integer currentPage, Integer pageSize) {
        Page<Account> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getUserId, userId);
        return this.page(page, wrapper);
    }
}
